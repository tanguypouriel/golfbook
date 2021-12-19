package com.mindeurfou.golfbook.ui.prepareGame

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.databinding.FragmentPrepareGameBinding
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class PrepareGameFragment : Fragment() {

    private var _binding: FragmentPrepareGameBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PrepareGameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrepareGameBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(PrepareGameEvent.GetGameDetailsEvent)

        return binding.root
    }


    private fun setupUI() {
        binding.startGameBtn.setOnClickListener { onClickStartBtn() }
    }

    private fun onClickStartBtn() {
        viewModel.setStateEvent(PrepareGameEvent.LaunchGameEvent)
    }

    private fun subscribeObservers() {
        viewModel.gameLaunchedId.observe(viewLifecycleOwner) { observeGameLaunched(it) }
        viewModel.gameDetails.observe(viewLifecycleOwner) { observeGameDetails(it) }
    }

    private fun observeGameDetails(dataState: DataState<GameDetails>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                val gameDetails: GameDetails = dataState.data
                binding.courseName.text = gameDetails.courseName
                bindStars(3) // TODO
                binding.numberOfHoles.text = getString(R.string.numberOfHoles, 18)
                binding.name.text = gameDetails.name
                binding.date.text = "18/06/2021" // TODO

                val adapter = ArrayAdapter(requireContext(), R.layout.item_course_name, ScoringSystem.toList(requireContext()))
                binding.editTextScoring.setAdapter(adapter)

                bindPlayers(gameDetails.players)

                binding.progressBar.hide()
                showData()
            }
        }
    }

    private fun observeGameLaunched(dataState: DataState<Int>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()
                navigateToInGameFragments(dataState.data)
            }
        }
    }

    private fun navigateToInGameFragments(gameId: Int){
        val action = PrepareGameFragmentDirections.actionPrepareGameFragmentToInGameFragment(gameId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindPlayers(players: List<Player>) {
        players.forEachIndexed { index, player ->
            when(index+1) {
                1 -> {
                    binding.imagePlayer1.setImageResource(player.drawableResourceId)
                    binding.namePlayer1.text = player.username
                }
                2 -> {
                    binding.imagePlayer2.setImageResource(player.drawableResourceId)
                    binding.namePlayer2.text = player.username
                }
                3 -> {
                    binding.imagePlayer3.setImageResource(player.drawableResourceId)
                    binding.namePlayer3.text = player.username
                }
                4 -> {
                    binding.imagePlayer4.setImageResource(player.drawableResourceId)
                    binding.namePlayer4.text = player.username
                }
                else -> {}
            }
        }
    }

    private fun bindStars(stars: Int) {
        for (i in 1..stars) {
            when (i) {
                1 -> binding.starBall1.setImageResource(R.drawable.ic_ball_full)
                2 -> binding.starBall2.setImageResource(R.drawable.ic_ball_full)
                3 -> binding.starBall3.setImageResource(R.drawable.ic_ball_full)
                4 -> binding.starBall4.setImageResource(R.drawable.ic_ball_full)
                5 -> binding.starBall5.setImageResource(R.drawable.ic_ball_full)
            }
        }
    }

    private fun showData() {
        val set: MutableList<Animator> = mutableListOf()

        val animatedViews : List<View> = listOf(
            binding.courseContainer,
            binding.gameDetailsContainer,
            binding.textInputScoring,
            binding.playersContainer,
            binding.startGameBtn,
        )

        animatedViews.forEach {
            AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_show_animator).apply {
                setTarget(it)
                set.add(this)
            }
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(set)
        animatorSet.start()
    }
}