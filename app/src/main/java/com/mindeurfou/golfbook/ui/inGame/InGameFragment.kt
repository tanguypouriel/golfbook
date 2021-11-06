package com.mindeurfou.golfbook.ui.inGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindeurfou.golfbook.R
import com.google.android.material.tabs.TabLayoutMediator
import com.mindeurfou.golfbook.databinding.FragmentInGameBinding

class InGameFragment : Fragment(R.layout.fragment_in_game) {

    private var _binding: FragmentInGameBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInGameBinding.inflate(inflater, container, false)

        val adapter = InGameAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0)
                "Groupé"
            else
                "Individuel"
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}