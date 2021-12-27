package com.mindeurfou.golfbook.ui.gameDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.databinding.FragmentGameDetailsBinding
import com.mindeurfou.golfbook.interactors.gameDetails.GameDetailsEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.reveal
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
                binding.scoreBookView.par = gameDetails.par
                binding.scoreBookView.scoreBook = gameDetails.scoreBook!!
                binding.scoreBookView.visibility = View.VISIBLE

                binding.scoringSystem.text = gameDetails.scoringSystem.toString()
                binding.courseName.text = gameDetails.courseName
                bindPlayers(gameDetails.players)

                listOf(
                        binding.title,
                        binding.scoringSystem,
                        binding.scoringSystemHint,
                        binding.courseName,
                        binding.courseNameHint,
                        binding.playersTitle,
                        binding.playersLayout,
                        binding.scoreBookView
                ).reveal(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindPlayers(players: List<Player>) {
        players.forEachIndexed { index, player ->
            when (index + 1) {
                1 -> {
                    binding.imageAvatar1.setImageResource(player.drawableResourceId)
                    binding.player1Name.text = player.username
                }
                2 -> {
                    binding.imageAvatar2.setImageResource(player.drawableResourceId)
                    binding.player2Name.text = player.username
                }
                3 -> {
                    binding.imageAvatar3.setImageResource(player.drawableResourceId)
                    binding.player3Name.text = player.username
                }
                4 -> {
                    binding.imageAvatar4.setImageResource(player.drawableResourceId)
                    binding.player4Name.text = player.username
                }
                else -> {}
            }
        }
    }
}