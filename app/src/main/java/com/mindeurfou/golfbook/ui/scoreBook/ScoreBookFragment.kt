package com.mindeurfou.golfbook.ui.scoreBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.databinding.FragmentScoreBookBinding
import com.mindeurfou.golfbook.interactors.scoreBook.ScoreBookEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class ScoreBookFragment : Fragment(R.layout.fragment_score_book) {

    private var _binding: FragmentScoreBookBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ScoreBookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBookBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(ScoreBookEvent.GetScoreBookEvent)

        return binding.root
    }

    private fun setupUI() {

    }

    private fun subscribeObservers() {
        viewModel.scoreBook.observe(viewLifecycleOwner) { observeScoreBook(it) }
    }

    private fun observeScoreBook(dataState: DataState<ScoreBook>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()
                binding.scoreBookView.scoreBook = dataState.data
                binding.scoreBookView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}