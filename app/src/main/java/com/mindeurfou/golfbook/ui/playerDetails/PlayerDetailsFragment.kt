package com.mindeurfou.golfbook.ui.playerDetails

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.databinding.FragmentPlayerDetailsBinding
import com.mindeurfou.golfbook.interactors.playerDetails.PlayerDetailsEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.setAvatarResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerDetailsFragment : Fragment(R.layout.fragment_player_details){

    private val navArgs: PlayerDetailsFragmentArgs by navArgs()

    private var _binding: FragmentPlayerDetailsBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: PlayerDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerDetailsBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(PlayerDetailsEvent.GetPlayerEvent)
        viewModel.setStateEvent(PlayerDetailsEvent.CheckIfIsSelf)

        showFragmentScreen()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {}

    private fun subscribeObservers() {
        viewModel.player.observe(viewLifecycleOwner) { observePlayer(it) }
        viewModel.isSelf.observe(viewLifecycleOwner) { observeIsSelf(it) }
    }

    private fun observeIsSelf(dataState: DataState<Boolean>) {
        when (dataState) {
            is DataState.Success -> {
                if (dataState.data)
                    binding.modifyProfileButton.visibility = View.VISIBLE
            }
            else -> {}
        }
    }

    private fun observePlayer(dataState: DataState<Player>) {
        when (dataState) {
            is DataState.Success -> {
                val player = dataState.data
                binding.imageAvatar.setAvatarResource(player.drawableResourceId)
                binding.playerUsername.text = player.username
                binding.nameText.text = player.name
                binding.lastNameText.text = player.lastName
            }
            else -> {}
        }
    }

    private fun showFragmentScreen() {
        AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_show_animator).apply {
            setTarget(binding.root)
            startDelay = 1000
            start()
        }
    }
}
