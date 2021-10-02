package com.mindeurfou.golfbook.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.databinding.FragmentTournamentsBinding
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@AndroidEntryPoint
@ExperimentalSerializationApi
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

        viewModel.setStateEvent(TournamentsEvent.GetTournamentsEvent())

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {}

    private fun subscribeObservers() {
        viewModel.tournaments.observe(viewLifecycleOwner) { observeTournaments(it) }
    }

    private fun observeTournaments(dataState: DataState<List<Tournament>>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Success -> {
                binding.progressBar.hide()
                binding.recyclerTournaments.adapter = TournamentAdapter(dataState.data) {
                    navigateToTournamentDetails()
                }
            }
            is DataState.Failure -> binding.progressBar.hide()
        }
    }

    private fun navigateToTournamentDetails() {
        findNavController().navigate(R.id.action_tournamentsFragment_to_tournamentDetailsFragment)
    }

}