package com.mindeurfou.golfbook.ui.playerDetails

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.databinding.FragmentPlayerDetailsBinding
import com.mindeurfou.golfbook.interactors.playerDetails.PlayerDetailsEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.setAvatarResource
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerDetailsFragment : Fragment(R.layout.fragment_player_details){

    private var _binding: FragmentPlayerDetailsBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: PlayerDetailsViewModel by viewModels()

    private var startInstant: Long? = null

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

        showProgressBar()
        startInstant = SystemClock.elapsedRealtime()
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
                if (dataState.data) {
                    binding.modifyProfileButton.visibility = View.VISIBLE
                    binding.modifyProfileButton.setOnClickListener { onModifyBtnClick() }
                }
            }
            else -> {}
        }
    }

    private fun observePlayer(dataState: DataState<Player>) {
        when (dataState) {
            is DataState.Success -> {
                binding.progressBar.hide()
                val player = dataState.data
                binding.imageAvatar.setAvatarResource(player.drawableResourceId)
                binding.playerUsername.text = player.username
                binding.nameText.text = player.name
                binding.lastNameText.text = player.lastName
                showData()
            }
            is DataState.Failure -> binding.progressBar.hide()
            else -> {}
        }
    }

    private fun onModifyBtnClick() {
        viewModel.player.value?.let { dataState ->
            if (dataState !is DataState.Success)
                return Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()

            navigateToModifyPlayerFragment(dataState.data)
        }
    }

    private fun navigateToModifyPlayerFragment(player: Player) {
        val action = PlayerDetailsFragmentDirections.actionPlayerDetailsFragmentToModifyPlayerFragment(player)
        findNavController().navigate(action)
    }

    private fun showProgressBar() {
        binding.progressBar.show()
        AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_show_animator).apply {
            setTarget(binding.progressBar)
            startDelay = 1000
            start()
        }
    }

    private fun showData() {
        val now = SystemClock.elapsedRealtime()
        val delay = if (now - startInstant!! < 1000)
            1000L
        else
            0L
        val set: MutableList<Animator> = mutableListOf()

        val animatedViews : List<View> = listOf(
            binding.playerUsername,
            binding.imageAvatar,
            binding.nameHint,
            binding.nameText,
            binding.lastNameHint,
            binding.lastNameText,
            binding.myStats,
            binding.cardView,
            binding.modifyProfileButton
        )

        animatedViews.forEach {
            AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_show_animator).apply {
                setTarget(it)
                startDelay = delay
                set.add(this)
            }
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(set)
        animatorSet.start()
    }
}
