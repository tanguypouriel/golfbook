package com.mindeurfou.golfbook.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentGamesBinding

class GamesFragment : Fragment(R.layout.fragment_games) {

    private var _binding: FragmentGamesBinding? = null
    private val binding: FragmentGamesBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        return binding.root
    }

    private fun setupUI() {
        binding.floatingActionButton.setOnClickListener {
            navigateToCreatePlayerFragment()
        }
    }

    private fun navigateToCreatePlayerFragment() {
        findNavController().navigate(R.id.action_gamesFragment_to_createGameFragment)
    }

    private fun subscribeObservers() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}