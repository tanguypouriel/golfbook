package com.mindeurfou.golfbook.ui.prepareGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.databinding.FragmentPrepareGameBinding
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameEvent
import com.mindeurfou.golfbook.ui.GameListener
import com.mindeurfou.golfbook.ui.MainActivityViewModel
import com.mindeurfou.golfbook.utils.*
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.snack
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.specific
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

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var playersReadyDialog : PlayersReadyDialog
    private lateinit var addPlayerDialog : AddPlayerDialog
    private var playersReadyDialogShown: Boolean = false
    private var addPlayerDialogShown: Boolean = false
    private var firstDisplay = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.setStateEvent(PrepareGameEvent.LeaveGameEvent)
        }
    }

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

        addPlayerDialog = AddPlayerDialog(
            getPlayersEvent = { viewModel.setStateEvent(PrepareGameEvent.GetPlayersEvent) },
            existingPlayers = viewModel.existingPlayers,
            object : AddPlayerDialogListener {

            override fun onDialogPositiveClick(dialog: AddPlayerDialog) {
                val name = dialog.nameInput.editText?.text.toString().trim()
                val lastName = dialog.lastNameInput.editText?.text.toString().trim()
                val username = dialog.usernameInput.editText?.text.toString().trim()
                val avatarId = dialog.imageAvatar.id

                if (dialog.togglePlayer.checkedButtonId == R.id.btnNew)
                    viewModel.setStateEvent(PrepareGameEvent.CreateAndAddPlayerEvent(
                        name, lastName, username, avatarId
                        )
                    )
                else
                    viewModel.setStateEvent(PrepareGameEvent.AddPlayerEvent(dialog.selectedPlayer))
            }

            override fun onDialogNegativeClick(dialog: AddPlayerDialog) {
                addPlayerDialogShown = false
                dialog.dismiss()
            }

        })

        playersReadyDialog = PlayersReadyDialog(object : DialogListener {

            override fun onDialogPositiveClick(dialog: DialogFragment) =
                viewModel.setStateEvent(PrepareGameEvent.AcceptGameStartEvent)

            override fun onDialogNegativeClick(dialog: DialogFragment) {
                viewModel.setStateEvent(PrepareGameEvent.RejectGameStartEvent)
                playersReadyDialogShown = false
                dialog.dismiss()
            }

        }).apply {
            isCancelable = false
        }
    }

    private fun onClickStartBtn() {
        viewModel.setStateEvent(PrepareGameEvent.TryStartGameEvent)
    }

    private fun subscribeObservers() {
        viewModel.gameDetails.observe(viewLifecycleOwner) { observeGameDetails(it) }
        viewModel.course.observe(viewLifecycleOwner) { observeCourse(it) }
        viewModel.playersReady.observe(viewLifecycleOwner) { observePlayersReady(it) }
        viewModel.playerAccepted.observe(viewLifecycleOwner) { observePlayerAccepted(it) } // from addPlayerDialog
        viewModel.status.observe(viewLifecycleOwner) { observeStatus(it) }
    }

    private fun observeStatus(dataState: DataState<Unit>) {
        when (dataState) {
            is DataState.Failure -> {
                dataState.errors?.let { errorMessages ->
                    makeSnackbar(binding.root, errorMessages)
                }
            }
            else -> {}
        }
    }

    private fun observeCourse(dataState: DataState<Course>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBarCircular.show()
            is DataState.Failure -> {
                binding.progressBarCircular.hide()
                dataState.errors?.let {
                    makeSnackbar(binding.courseContainer, it)
                }
            }
            is DataState.Success -> {
                binding.progressBarCircular.hide()
                binding.courseName.text = dataState.data.name
                bindStars(dataState.data.stars)
                binding.numberOfHoles.text = getString(R.string.numberOfHoles, dataState.data.numberOfHoles)
                binding.courseStaringLayout.show()
            }
        }
    }

    private fun observeGameDetails(dataState: DataState<GameDetails>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { makeSnackbar(binding.root, it) }
            }
            is DataState.Success -> {
                val gameDetails: GameDetails = dataState.data
                binding.name.text = gameDetails.name
                binding.date.text = gameDetails.date.print()

                binding.editTextScoring.setText(gameDetails.scoringSystem.toString())
                val adapter = ArrayAdapter(requireContext(), R.layout.item_course_name, ScoringSystem.toList(requireContext()))
                binding.editTextScoring.setAdapter(adapter)

                bindPlayers(gameDetails.players)

                binding.progressBar.hide()

                if (firstDisplay) {
                    firstDisplay = false
                    listOf(
                            binding.courseContainer,
                            binding.gameDetailsContainer,
                            binding.textInputScoring,
                            binding.playersContainer,
                            binding.startGameBtn,
                    ).reveal(requireContext())

                    viewModel.setStateEvent(PrepareGameEvent.GetCourseEvent(gameDetails.courseName))
                    subscribeToGameNotification(gameDetails.id)
                }

                if (gameDetails.state == GBState.STARTING && !playersReadyDialogShown) {
                    playersReadyDialogShown = true
                    playersReadyDialog.show(parentFragmentManager, "playersReady")
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

    private fun observePlayerAccepted(dataState: DataState<Unit>) {
        when (dataState) {
            is DataState.Loading -> addPlayerDialog.progressBar.show()
            is DataState.Failure -> {
                addPlayerDialog.progressBar.hide()
                dataState.errors?.let { errors ->
                    val sorted = ErrorMessages.sort(errors)
                    sorted[specific]?.let {
                        it.forEach { errorMessage ->
                            when (errorMessage) {
                                ErrorMessages.NAME_EMPTY -> addPlayerDialog.nameInput.error = errorMessage.toString()
                                ErrorMessages.LASTNAME_EMPTY -> addPlayerDialog.lastNameInput.error = errorMessage.toString()
                                ErrorMessages.USERNAME_EMPTY -> addPlayerDialog.usernameInput.error = errorMessage.toString()
                                else -> {}
                            }
                        }

                        sorted[snack]?.let { snackErrors ->
                            makeSnackbar(binding.root, snackErrors)
                        }
                    }
                }
            }
            is  DataState.Success -> {
                addPlayerDialogShown = false
                addPlayerDialog.dismiss()
            }
        }
    }

    private fun subscribeToGameNotification(gameId: Int) {
        mainViewModel.openGameSocket(gameId, object : GameListener {

            override fun onGameDetailsNotification() =
                viewModel.setStateEvent(PrepareGameEvent.GetGameDetailsEvent)

            override fun onScoreNotification() {}

            override fun onPlayersReadyNotification() =
                viewModel.setStateEvent(PrepareGameEvent.CheckPlayerReadyEvent)

        })
    }

    private fun observePlayersReady(dataState: DataState<List<String>>) {
        when (dataState) {
            is DataState.Loading -> {}
            is DataState.Failure -> {}
            is DataState.Success -> {
                val playersReady = dataState.data

                if (viewModel.gameDetails.value !is DataState.Success) return
                val sizeAll = (viewModel.gameDetails.value as DataState.Success<GameDetails>).data.players.size

                val sizeReady = playersReady.size
                val progress = (sizeReady * 100) / sizeAll
                playersReadyDialog.progressLinear!!.setSmoothProgress(progress)

                if (playersReadyDialog.positiveButton?.visibility == View.VISIBLE ||
                    playersReadyDialog.negativeButton?.visibility == View.VISIBLE) {

                    if (playersReady.contains(viewModel.selfName)) {
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
                    binding.imagePlayer1.setImageResource(player.avatarId)
                    binding.namePlayer1.text = player.username
                }
                2 -> {
                    binding.imagePlayer2.setImageResource(player.avatarId)
                    binding.namePlayer2.text = player.username
                }
                3 -> {
                    binding.imagePlayer3.setImageResource(player.avatarId)
                    binding.namePlayer3.text = player.username
                }
                4 -> {
                    binding.imagePlayer4.setImageResource(player.avatarId)
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
        addPlayerDialog.show(parentFragmentManager, "addPlayer")
        addPlayerDialogShown = true
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