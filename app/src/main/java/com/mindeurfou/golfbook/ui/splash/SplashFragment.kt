package com.mindeurfou.golfbook.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.MainActivity
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentSplashBinding
import com.mindeurfou.golfbook.interactors.splash.SplashEvent
import com.mindeurfou.golfbook.ui.hillView.HillPosition
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null

    private val binding
        get() = _binding!!

    val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            viewModel.setStateEvent(SplashEvent.CheckToken)
        }

        subscribeObservers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.validToken.observe(viewLifecycleOwner) { observerValidToken(it) }
    }

    private fun observerValidToken(dataState : DataState<Boolean>) {
        when (dataState) {
            is DataState.Success -> {
                if (dataState.data)
                    navigateToTournamentsFragment()
                else
                    navigateToConnectionFragment()
            }
            is DataState.Failure -> navigateToConnectionFragment()
        }
    }

    private fun navigateToConnectionFragment() {
        val navigationAction = SplashFragmentDirections.actionSplashFragmentToConnectionFragment()
        findNavController().navigate(navigationAction)
    }

    private fun navigateToTournamentsFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_tournamentsFragment)
    }
}