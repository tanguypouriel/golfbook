package com.mindeurfou.golfbook.ui.prepareGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindeurfou.golfbook.databinding.FragmentPrepareGameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrepareGameFragment : Fragment() {

    private var _binding: FragmentPrepareGameBinding? = null
    private val binding
        get() = _binding!!

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

    }

    private fun subscribeObservers() {

    }

    private fun navigateToSomeGameScreen(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}