package com.mindeurfou.golfbook.ui.createGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.databinding.FragmentCreateGameBinding
import com.mindeurfou.golfbook.interactors.createGame.CreateGameEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
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
        viewModel.createdGame.observe(viewLifecycleOwner) { observeCreatedGame(it) }
        viewModel.coursesNames.observe(viewLifecycleOwner) { observeCoursesNames(it) }
    }

    private fun observeCoursesNames(dataState: DataState<List<String>>) {
        when(dataState) {
            is DataState.Success -> {
                val adapter = ArrayAdapter(requireContext(), R.layout.item_course_name, dataState.data)
                binding.editTextCourse.setAdapter(adapter)
            }
        }

    }

    private fun observeCreatedGame(dataState: DataState<Game>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()
                navigateToPrepareGameFragment(dataState.data.id)
            }
        }
    }

    private fun setupUI() {
        val defaultGameName = getString(R.string.defautlGameName, LocalDate.now().toString())
        binding.gameNameEditText.setText(defaultGameName)
        binding.createGameBtn.setOnClickListener { createGameOnClick() }
    }

    private fun createGameOnClick() {
        val name = binding.gameNameEditText.text.toString()
        val courseName = binding.editTextCourse.text.toString()

        viewModel.setEventState(CreateGameEvent.SendGameEvent(name, courseName))
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