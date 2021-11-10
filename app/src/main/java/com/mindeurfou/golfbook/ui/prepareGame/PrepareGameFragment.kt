package com.mindeurfou.golfbook.ui.prepareGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
}