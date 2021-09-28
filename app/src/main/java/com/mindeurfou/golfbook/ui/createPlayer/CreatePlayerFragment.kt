package com.mindeurfou.golfbook.ui.createPlayer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentCreatePlayerBinding

class CreatePlayerFragment : Fragment(R.layout.fragment_create_player) {

    private var _binding: FragmentCreatePlayerBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: CreatePlayerViewModel by viewModels()

    var backdropShown: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePlayerBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        binding.imageAvatar.setOnClickListener(
            AvatarImageClickListener(requireContext(), binding.mainLayout, true, this)
        )
        setOnClickListenersAvatars()

        binding.okBtn.setOnClickListener { navigateToTournamentsFragment() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToTournamentsFragment() {
        findNavController().navigate(R.id.action_createPlayerFragment_to_tournamentsFragment)
    }


    private fun setOnClickListenersAvatars() {
        binding.man1.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.man2.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.man3.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.man4.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.man5.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.man6.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.man7.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.man8.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman1.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman2.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman3.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman4.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman5.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman6.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman7.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
        binding.woman8.setOnClickListener(AvatarImageClickListener(requireContext(), binding.mainLayout, fragment = this))
    }
}