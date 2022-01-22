package com.mindeurfou.golfbook.ui.createPlayer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.ui.StartActivity
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentCreatePlayerBinding
import com.mindeurfou.golfbook.interactors.createPlayer.CreatePlayerEvent
import com.mindeurfou.golfbook.ui.playerDetails.PlayerConfigFragment
import com.mindeurfou.golfbook.utils.*
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.snack
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.specific
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlayerFragment : Fragment(R.layout.fragment_create_player), PlayerConfigFragment {

    override val activity: StartActivity by lazy { requireActivity() as StartActivity }

    private var _binding: FragmentCreatePlayerBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: CreatePlayerViewModel by viewModels()

    override var backdropShown: Boolean = false

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

    private fun setupUI() {

        binding.imageAvatar.setOnClickListener(
            AvatarImageClickListener(requireContext(), binding.mainLayout, binding.avatarGrid,true, this)
        )
        setOnClickListenersAvatars()

        binding.okBtn.setOnClickListener { okBtnOnClick() }
    }

    private fun subscribeObservers() {
        viewModel.playerCreated.observe(viewLifecycleOwner) { observePlayerCreated(it) }
    }

    private fun observePlayerCreated(dataState: DataState<Unit>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Success -> {
                binding.progressBar.hide()
                activity.navigateToMainActivity()
            }
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { handleErrors(it) }
            }
        }
    }

    private fun handleErrors(errors: List<ErrorMessages>) {
        val sorted = ErrorMessages.sort(errors)
        sorted[snack]?.let { makeSnackbar(binding.root, it) }
        sorted[specific]?.forEach {
            when (it) {
                ErrorMessages.USERNAME_TAKEN -> binding.usernameInput.error = it.toString()
                ErrorMessages.USERNAME_EMPTY -> binding.usernameInput.error = it.toString()
                ErrorMessages.NAME_EMPTY -> binding.nameInput.error = it.toString()
                ErrorMessages.LASTNAME_EMPTY -> binding.lastNameInput.error = it.toString()
                ErrorMessages.PASSWORD_EMPTY -> binding.passwordInput.error = it.toString()
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun okBtnOnClick() {
        val name = binding.nameInput.editText!!.text.toString().trim()
        val lastname = binding.lastNameInput.editText!!.text.toString().trim()
        val username = binding.usernameInput.editText!!.text.toString().trim()
        val password = binding.passwordInput.editText!!.text.toString().trim()
        val avatarId = binding.imageAvatar.id
        viewModel.setStateEvent(CreatePlayerEvent.SendPlayerInfoEvent(name, lastname, username, password, avatarId))
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