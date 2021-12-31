package com.mindeurfou.golfbook.ui.createGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import com.mindeurfou.golfbook.databinding.FragmentCreateGameBinding
import com.mindeurfou.golfbook.interactors.createGame.CreateGameEvent
import com.mindeurfou.golfbook.utils.*
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.snack
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.specific
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import java.time.LocalDate

@ExperimentalSerializationApi
@AndroidEntryPoint
class CreateGameFragment : Fragment(R.layout.fragment_create_game){

    private var _binding: FragmentCreateGameBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CreateGameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGameBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setEventState(CreateGameEvent.GetCoursesNames)

        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.createdGameId.observe(viewLifecycleOwner) { observeCreatedGame(it) }
        viewModel.coursesNames.observe(viewLifecycleOwner) { observeCoursesNames(it) }
    }

    private fun observeCoursesNames(dataState: DataState<List<String>>) {
        when(dataState) {
            is DataState.Success -> {
                val adapter = ArrayAdapter(requireContext(), R.layout.item_course_name, dataState.data)
                binding.editTextCourse.setAdapter(adapter)
            }
            is DataState.Loading -> {}
            is DataState.Failure -> Snackbar.make(binding.root, R.string.networkError, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun observeCreatedGame(dataState: DataState<Int>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { handleErrors(it) }
            }
            is DataState.Success -> {
                binding.progressBar.hide()
                navigateToPrepareGameFragment(dataState.data)
            }
        }
    }

    private fun handleErrors(errors: List<ErrorMessages>) {
        val sorted = ErrorMessages.sort(errors)
        sorted[snack]?.let { makeSnackbar(binding.root, it) }
        sorted[specific]?.let { specificErrors ->
            specificErrors.forEach {
                when (it) {
                    ErrorMessages.NAME_EMPTY -> binding.gameNameInputLayout.error = it.toString()
                    ErrorMessages.COURSE_EMPTY -> binding.textInputCourse.error = it.toString()
                    ErrorMessages.SCORING_SYSTEM_EMPTY -> binding.textInputScoring.error = it.toString()
                    ErrorMessages.UNKNOWN_SCORING_SYSTEM -> binding.editTextScoring.error = it.toString()
                    else -> {}
                }
            }
        }
    }

    private fun setupUI() {
        val defaultGameName = getString(R.string.defautlGameName, LocalDate.now().print())
        binding.gameNameEditText.setText(defaultGameName)

        val defaultScoringSystem = getString(R.string.strokePlay)
        binding.editTextScoring.setText(defaultScoringSystem)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_course_name, ScoringSystem.toList(requireContext()))
        binding.editTextScoring.setAdapter(adapter)

        binding.createGameBtn.setOnClickListener { createGameOnClick() }
    }

    private fun createGameOnClick() {
        val name = binding.gameNameEditText.text.toString().trim()
        val courseName = binding.editTextCourse.text.toString().trim()
        val scoringSystem = binding.editTextScoring.text.toString().trim()

        viewModel.setEventState(CreateGameEvent.SendGameEvent(name, courseName, scoringSystem))
    }

    private fun navigateToPrepareGameFragment(gameId: Int) {
        val action = CreateGameFragmentDirections.actionCreateGameFragmentToPrepareGameFragment(gameId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}