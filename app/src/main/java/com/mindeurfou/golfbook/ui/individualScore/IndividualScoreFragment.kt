package com.mindeurfou.golfbook.ui.individualScore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentIndividualScoreBinding

class IndividualScoreFragment(
    private val gameId: Int
) : Fragment(R.layout.fragment_individual_score) {

    private var _binding: FragmentIndividualScoreBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndividualScoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}