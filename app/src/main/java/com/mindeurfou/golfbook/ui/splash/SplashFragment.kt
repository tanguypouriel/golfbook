package com.mindeurfou.golfbook.ui.splash


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null

    private val viewModel: SplashViewModel by viewModels()

    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val credentials = viewModel.getCredentials()
            navigateToConnectionFragment(credentials.first, credentials.second)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToConnectionFragment(username: String?, password : String?) {
        val navigationAction = SplashFragmentDirections.actionSplashFragmentToConnectionFragment(username, password)
        findNavController().navigate(navigationAction)
    }
}