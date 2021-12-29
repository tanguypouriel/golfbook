package com.mindeurfou.golfbook.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.databinding.FragmentGamesBinding
import com.mindeurfou.golfbook.interactors.games.GamesEvent
import com.mindeurfou.golfbook.utils.*
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.snack
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.specific
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@AndroidEntryPoint
@ExperimentalSerializationApi
class GamesFragment : Fragment(R.layout.fragment_games) {

    private var _binding: FragmentGamesBinding? = null
    private val binding: FragmentGamesBinding
        get() = _binding!!

    private val viewModel: GamesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(GamesEvent.GetGamesEvent)

        return binding.root
    }

    private fun setupUI() {
        binding.floatingActionButton.setOnClickListener {
            navigateToOnboardGameFragment()
        }
    }

    private fun subscribeObservers() {
        viewModel.games.observe(viewLifecycleOwner) { observeGames(it) }
    }

    private fun observeGames(dataState: DataState<List<Game>>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { errors ->
                    val sorted = ErrorMessages.sort(errors)
                    sorted[snack]?.let { makeSnackbar(binding.root, it) }
                    sorted[specific]?.forEach {
                        when (it) {
                            ErrorMessages.NO_GAMES -> showNoGames()
                            else -> {}
                        }
                    }

                }
            }
            is DataState.Success -> {
                binding.progressBar.hide()
                binding.recyclerGames.adapter = GamesAdapter(dataState.data) { game ->
                    navigateToGameDetails(game.id)
                }
            }
        }
    }

    private fun showNoGames() {
        binding.noGamesImage.show()
        binding.noGamesText.show()
    }

    private fun navigateToOnboardGameFragment() {
        findNavController().navigate(R.id.action_gamesFragment_to_onboardGameFragment)
    }

    private fun navigateToGameDetails(gameId: Int) {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(gameId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}