package com.mindeurfou.golfbook.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentConnectionBinding
import com.mindeurfou.golfbook.databinding.FragmentSplashBinding
import com.mindeurfou.golfbook.ui.common.BaseFragment

class ConnectionFragment : BaseFragment(R.layout.fragment_splash) {

    private var _binding: FragmentConnectionBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConnectionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}