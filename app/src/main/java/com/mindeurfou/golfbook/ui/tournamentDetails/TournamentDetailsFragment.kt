package com.mindeurfou.golfbook.ui.tournamentDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentTournamentDetailsBinding

class TournamentDetailsFragment : Fragment(R.layout.fragment_tournament_details){

    private var _binding: FragmentTournamentDetailsBinding? = null

    private val binding
        get() = _binding!!

//    private val viewModel:

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTournamentDetailsBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {

    }

    private fun subscribeObservers() {

    }
}