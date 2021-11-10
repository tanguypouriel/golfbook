package com.mindeurfou.golfbook.ui.gameDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.databinding.FragmentGameDetailsBinding
import com.mindeurfou.golfbook.interactors.gameDetails.GameDetailsEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class GameDetailsFragment : Fragment(R.layout.fragment_game_details){

    private var _binding: FragmentGameDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: GameDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(GameDetailsEvent.GetGameDetailsEvent)

        return binding.root
    }

    private fun setupUI() {

    }

    private fun subscribeObservers() {
        viewModel.gameDetails.observe(viewLifecycleOwner) { observeGameDetails(it) }
    }

    private fun observeGameDetails(dataState: DataState<GameDetails>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()
                val gameDetails = dataState.data
                binding.title.text = gameDetails.name
                binding.scoreBookView.scoreBook = gameDetails.scoreBook!!
                binding.scoreBookView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}