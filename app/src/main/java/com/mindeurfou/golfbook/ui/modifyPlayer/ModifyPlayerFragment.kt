package com.mindeurfou.golfbook.ui.modifyPlayer

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
import com.mindeurfou.golfbook.databinding.FragmentCreatePlayerBinding
import com.mindeurfou.golfbook.interactors.modifyPlayer.ModifyPlayerEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyPlayerFragment : Fragment(R.layout.fragment_create_player){

    private val navArgs: ModifyPlayerFragmentArgs by navArgs()

    private var _binding: FragmentCreatePlayerBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ModifyPlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlayerBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.modificationAccepted.observe(viewLifecycleOwner) { observerModificationAccepted(it) }
    }

    private fun observerModificationAccepted(dataState: DataState<Boolean>) {
        when (dataState) {
            is DataState.Loading -> { binding.progressBar.show() }
            is DataState.Failure -> {
                binding.progressBar.hide()
                Toast.makeText(requireContext(), dataState.exception.message, Toast.LENGTH_SHORT).show()
            }
            is DataState.Success -> {
                binding.progressBar.hide()
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                // popup backstack
            }
        }
    }

    private fun setupUI() {
        binding.createPlayerLabel.text = getString(R.string.modify)
        binding.passwordInput.visibility = View.GONE
        binding.nameInput.editText!!.setText(navArgs.player.name)
        binding.lastNameInput.editText!!.setText(navArgs.player.lastName)
        binding.usernameInput.editText!!.setText(navArgs.player.username)
        binding.imageAvatar.setImageResource(navArgs.player.drawableResourceId)

        binding.okBtn.setOnClickListener { onOkBtnClick() }
    }

    private fun onOkBtnClick() {
        val player = Player(
            id = navArgs.player.id,
            name = binding.nameInput.editText!!.text.toString(),
            lastName = binding.lastNameInput.editText!!.text.toString(),
            username = binding.usernameInput.editText!!.text.toString(),
            drawableResourceId = binding.imageAvatar.id
        )
        viewModel.setStateEvent(ModifyPlayerEvent.SendModificationEvent(player))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}