package com.mindeurfou.golfbook.ui.scoreCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentScoreCardBinding

class ScoreCardFragment : Fragment(R.layout.fragment_score_card) {

    private var _binding: FragmentScoreCardBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreCardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}