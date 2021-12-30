package com.mindeurfou.golfbook.ui.modifyPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mindeurfou.golfbook.ui.StartActivity
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.databinding.FragmentCreatePlayerBinding
import com.mindeurfou.golfbook.interactors.modifyPlayer.ModifyPlayerEvent
import com.mindeurfou.golfbook.ui.HillActivity
import com.mindeurfou.golfbook.ui.MainActivity
import com.mindeurfou.golfbook.ui.createPlayer.AvatarImageClickListener
import com.mindeurfou.golfbook.ui.playerDetails.PlayerConfigFragment
import com.mindeurfou.golfbook.utils.*
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.snack
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.specific
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyPlayerFragment : Fragment(R.layout.fragment_create_player), PlayerConfigFragment {

    private val navArgs: ModifyPlayerFragmentArgs by navArgs()

    private var _binding: FragmentCreatePlayerBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ModifyPlayerViewModel by viewModels()

    override var backdropShown = false
    override val activity: HillActivity by lazy { requireActivity() as HillActivity }

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
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { handleErrors(it) }
            }
            is DataState.Success -> {
                binding.progressBar.hide()
                if (dataState.data)
                    navigateToPlayerDetailsFragment(navArgs.player.id)
                else
                    Snackbar.make(binding.root, getString(R.string.networkError), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleErrors(errors: List<ErrorMessages>) {
        val sorted = ErrorMessages.sort(errors)
        sorted[snack]?.let { makeSnackbar(binding.root, it) }
        sorted[specific]?.let {
            it.forEach { message ->
                when(message) {
                    ErrorMessages.NAME_EMPTY -> binding.nameInput.error = message.toString()
                    ErrorMessages.LASTNAME_EMPTY -> binding.lastNameInput.error = message.toString()
                    ErrorMessages.USERNAME_EMPTY -> binding.usernameInput.error = message.toString()
                    else -> {}
                }
            }
        }
    }

    private fun setupUI() {
        binding.createPlayerLabel.text = getString(R.string.modify)
        binding.passwordInput.visibility = View.GONE
        binding.nameInput.editText!!.setText(navArgs.player.name)
        binding.lastNameInput.editText!!.setText(navArgs.player.lastName)
        binding.usernameInput.editText!!.setText(navArgs.player.username)
        binding.imageAvatar.setAvatarResource(navArgs.player.drawableResourceId)

        binding.imageAvatar.setOnClickListener(
            AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid,true, this)
        )
        setOnClickListenersAvatars()

        binding.okBtn.setOnClickListener { onOkBtnClick() }
    }

    private fun onOkBtnClick() {
        viewModel.setStateEvent(ModifyPlayerEvent.SendModificationEvent(
            id = navArgs.player.id,
            name = binding.nameInput.editText!!.text.toString().trim(),
            lastName = binding.lastNameInput.editText!!.text.toString().trim(),
            username = binding.usernameInput.editText!!.text.toString().trim(),
            drawableResourceId = binding.imageAvatar.id
            )
        )
    }

    private fun navigateToPlayerDetailsFragment(playerId: Int) {
        val action = ModifyPlayerFragmentDirections.actionModifyPlayerFragmentToPlayerDetailsFragment(playerId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListenersAvatars() {
        binding.man1.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_1)
        })
        binding.man2.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_2)
        })
        binding.man3.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_3)
        })
        binding.man4.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_4)
        })
        binding.man5.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_5)
        })
        binding.man6.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_6)
        })
        binding.man7.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_7)
        })
        binding.man8.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.man_8)
        })
        binding.woman1.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_1)
        })
        binding.woman2.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_2)
        })
        binding.woman3.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_3)
        })
        binding.woman4.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_4)
        })
        binding.woman5.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_5)
        })
        binding.woman6.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_6)
        })
        binding.woman7.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_7)
        })
        binding.woman8.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid, fragment = this) {
            binding.imageAvatar.setImageResource(R.drawable.woman_8)
        })
    }
}