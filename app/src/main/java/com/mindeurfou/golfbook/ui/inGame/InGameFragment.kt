package com.mindeurfou.golfbook.ui.inGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.navArgs
import com.mindeurfou.golfbook.R
import com.google.android.material.tabs.TabLayoutMediator
import com.mindeurfou.golfbook.databinding.FragmentInGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalAnimationApi
@ExperimentalSerializationApi
class InGameFragment : Fragment(R.layout.fragment_in_game) {

    private var _binding: FragmentInGameBinding? = null
    private val binding
        get() = _binding!!

    private val args: InGameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInGameBinding.inflate(inflater, container, false)

        val adapter = InGameAdapter(this, args.gameId)
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