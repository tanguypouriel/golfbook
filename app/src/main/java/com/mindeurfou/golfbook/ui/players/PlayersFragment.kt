package com.mindeurfou.golfbook.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentPlayersBinding

class PlayersFragment : Fragment(R.layout.fragment_players) {

    private var _binding: FragmentPlayersBinding? = null
    private val binding
        get() = _binding!!

//    private val viewModel: PlayersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {

        binding.recyclerPlayers.adapter = PlayerAdapter {
            navigateToPlayerDetails()
        }
        binding.recyclerPlayers.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun navigateToPlayerDetails() {
        findNavController().navigate(R.id.action_playersFragment_to_playerDetailsFragment)
    }
}