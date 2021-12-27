package com.mindeurfou.golfbook.ui.prepareGame

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.databinding.FragmentPrepareGameBinding
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameEvent
import com.mindeurfou.golfbook.interactors.splash.SplashEvent
import com.mindeurfou.golfbook.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class PrepareGameFragment : Fragment() {

    private var _binding: FragmentPrepareGameBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PrepareGameViewModel by viewModels()

    private lateinit var playersReadyDialog : PlayersReadyDialog
    private lateinit var addPlayerDialog : AddPlayerDialog
    private var playersReadyDialogShown: Boolean = false
    private var addPlayerDialogShown: Boolean = false
    private var firstDisplay = true

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

        addPlayerDialog = AddPlayerDialog(object : DialogListener {

            override fun onDialogPositiveClick(dialog: DialogFragment) {
                addPlayerDialogShown = false
            }

            override fun onDialogNegativeClick(dialog: DialogFragment) {
                addPlayerDialogShown = false
            }

        })

        playersReadyDialog = PlayersReadyDialog(object : DialogListener {

            override fun onDialogPositiveClick(dialog: DialogFragment) {
                navigateToInGameFragments(gameId = 1)
                playersReadyDialogShown = false
            }

            override fun onDialogNegativeClick(dialog: DialogFragment) {
                viewModel.setStateEvent(PrepareGameEvent.RejectGameStart)
                playersReadyDialogShown = false
            }

        }).apply {
            isCancelable = false
        }
    }

    private fun onClickStartBtn() {
        viewModel.setStateEvent(PrepareGameEvent.CheckPlayerReady)
    }

    private fun subscribeObservers() {
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
                if (firstDisplay) {
                    listOf(
                            binding.courseContainer,
                            binding.gameDetailsContainer,
                            binding.textInputScoring,
                            binding.playersContainer,
                            binding.startGameBtn,
                    ).reveal(requireContext())
                    firstDisplay = false
                }

                if (gameDetails.state == GBState.STARTING && !playersReadyDialogShown) {
                    playersReadyDialogShown = true
                    playersReadyDialog.show(parentFragmentManager, "playersReady")
                    playersReadyDialog.lifecycleScope.launchWhenResumed {
                        playerDialogDisplay(gameDetails)
                    }
                }
                else if (gameDetails.state == GBState.STARTING) {
                    playerDialogDisplay(gameDetails)
                }
                else if (gameDetails.state == GBState.INIT && playersReadyDialogShown) {
                    playersReadyDialog.dismiss()
                    playersReadyDialogShown = false
                } else if (gameDetails.state == GBState.PENDING) {
                    if (playersReadyDialogShown) playersReadyDialog.dismiss()
                    if (addPlayerDialogShown) addPlayerDialog.dismiss()

                    navigateToInGameFragments(gameId = gameDetails.id)
                }
            }
        }
    }

    private fun playerDialogDisplay(gameDetails: GameDetails) {
        val sizeReady = gameDetails.playersReady.size
        val sizeAll = gameDetails.players.size
        val progress = (sizeReady * 100) / sizeAll
        playersReadyDialog.progressLinear!!.setSmoothProgress(progress)

        if (playersReadyDialog.positiveButton?.visibility == View.VISIBLE ||
                playersReadyDialog.negativeButton?.visibility == View.VISIBLE) {

            if (gameDetails.playersReady.contains(viewModel.selfName)) {
                playersReadyDialog.positiveButton?.visibility = View.GONE
                playersReadyDialog.negativeButton?.visibility = View.GONE
            }
        }

        if (progress == 100) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(300)
                playersReadyDialog.progressLinear?.visibility = View.GONE
                delay(300)
                playersReadyDialog.progressCircular?.visibility = View.VISIBLE
            }
        }

        playersReadyDialog.dialogText?.text = getString(R.string.playersReady, sizeReady, sizeAll)
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

        val nbPlayers = players.size
        if (4 - nbPlayers > 0) {
            for (i in (nbPlayers+1)..4)
                setOnPlayerContainerClick(i)
        }
    }

    private fun setOnPlayerContainerClick(num: Int) {
        when(num) {
            1 -> binding.containerPlayer1.setOnClickListener { showAddPlayerDialog() }
            2 -> binding.containerPlayer2.setOnClickListener { showAddPlayerDialog() }
            3 -> binding.containerPlayer3.setOnClickListener { showAddPlayerDialog() }
            4 -> binding.containerPlayer4.setOnClickListener { showAddPlayerDialog() }
            else -> {}
        }
    }

    private fun showAddPlayerDialog() {
        addPlayerDialog?.let{
            it.show(parentFragmentManager, "addPlayer")
            addPlayerDialogShown = true
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
}