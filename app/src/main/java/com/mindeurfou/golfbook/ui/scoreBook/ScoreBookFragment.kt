package com.mindeurfou.golfbook.ui.scoreBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.databinding.FragmentScoreBookBinding
import com.mindeurfou.golfbook.interactors.scoreBook.ScoreBookEvent
import com.mindeurfou.golfbook.ui.GameListener
import com.mindeurfou.golfbook.ui.MainActivityViewModel
import com.mindeurfou.golfbook.ui.customViews.HoleInputItem
import com.mindeurfou.golfbook.ui.customViews.ScoreSummaryItem
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class ScoreBookFragment(
    private val gameId: Int
) : Fragment(R.layout.fragment_score_book) {

    private var _binding: FragmentScoreBookBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ScoreBookViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private var scoreSummaries: MutableList<ScoreSummaryItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBookBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(ScoreBookEvent.GetGameDetailsEvent(gameId))

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
                binding.scoreBookView.scoreBook = dataState.data.scoreBook!!
                binding.scoreBookView.par = dataState.data.par
                binding.scoreBookView.visibility = View.VISIBLE

                dataState.data.scoreSummaries.forEach { scoreSummary ->
                    val scoreSummaryItem = ScoreSummaryItem(requireContext())
                    scoreSummaryItem.scoreSummary = scoreSummary
                    binding.playersSummaryContainer.addView(scoreSummaryItem)
                    scoreSummaries.add(scoreSummaryItem)
                }

                mainViewModel.observeGameSocket(object : GameListener {
                    override fun onGameDetailsNotification() {
                        viewModel.setStateEvent(ScoreBookEvent.GetGameDetailsEvent(gameId))
                    }

                    override fun onScoreNotification() {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}