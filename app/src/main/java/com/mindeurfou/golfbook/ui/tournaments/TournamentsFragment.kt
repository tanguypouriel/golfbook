package com.mindeurfou.golfbook.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentTournamentsBinding

class TournamentsFragment : Fragment(R.layout.fragment_tournaments) {

    private var _binding: FragmentTournamentsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: TournamentsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTournamentsBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {

        binding.recyclerTournaments.adapter = TournamentAdapter {
            navigateToTournamentDetails()
        }
        binding.recyclerTournaments.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun subscribeObservers() {

    }

    private fun navigateToTournamentDetails() {
        findNavController().navigate(R.id.action_tournamentsFragment_to_tournamentDetailsFragment)
    }

}