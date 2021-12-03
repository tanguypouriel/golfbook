package com.mindeurfou.golfbook.ui.scoreInput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.databinding.FragmentScoreInputBinding
import com.mindeurfou.golfbook.interactors.scoreInput.ScoreInputEvent
import com.mindeurfou.golfbook.ui.customViews.ScoreCellData
import com.mindeurfou.golfbook.ui.customViews.ScoreInputListener
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class ScoreInputFragment(
    private val gameId: Int
) : Fragment(R.layout.fragment_score_input) {

    private var _binding: FragmentScoreInputBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ScoreInputViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreInputBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(ScoreInputEvent.GetGameDetailsEvent(gameId))
        return binding.root
    }

    private fun setupUI() {
        binding.scoreInputView.scoreInputListener = object : ScoreInputListener {

            override fun onScoreEntered(scoreCellData: ScoreCellData) {
                Toast.makeText(requireContext(), "$scoreCellData", Toast.LENGTH_SHORT).show()
                //            viewModel.setStateEvent(ScoreInputEvent.ScoreDialogEvent())
            }
        }
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
                binding.scoreInputView.par = dataState.data.par
                binding.scoreInputView.scoreBook = dataState.data.scoreBook!!
                binding.scoreInputView.scoreSummaries = dataState.data.scoreSummaries
                binding.scoreInputView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}