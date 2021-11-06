package com.mindeurfou.golfbook.ui.onboardGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.databinding.FragmentOnboardGameBinding
import com.mindeurfou.golfbook.interactors.onboardGame.OnBoardGameEvent
import com.mindeurfou.golfbook.ui.common.MarginItemDecoration
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@AndroidEntryPoint
@ExperimentalSerializationApi
class OnboardGameFragment : Fragment(R.layout.fragment_onboard_game) {

    private var _binding: FragmentOnboardGameBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: OnBoardGameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardGameBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(OnBoardGameEvent.CheckPendingGameEvent)

        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.pendingGames.observe(viewLifecycleOwner) { observePendingGames(it) }
    }

    private fun observePendingGames(dataState: DataState<List<Game>?>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()

                dataState.data?.let { games ->
                    binding.pendingGameRecycler.adapter = OnBoardGameAdapter(games) { game ->
                        navigateToPrepareGameFragment(game.id)
                    }
                } ?: Toast.makeText(requireContext(), "no pending games", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupUI() {
        binding.btnCreateGame.setOnClickListener { navigateToCreateGameFragment() }
        binding.pendingGameRecycler.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_margin).toInt()))
    }

    private fun navigateToCreateGameFragment() {
        val action = OnboardGameFragmentDirections.actionOnboardGameFragmentToCreateGameFragment()
        findNavController().navigate(action)
    }

    private fun navigateToPrepareGameFragment(gameId: Int) {
        val action = OnboardGameFragmentDirections.actionOnboardGameFragmentToPrepareGameFragment(gameId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}