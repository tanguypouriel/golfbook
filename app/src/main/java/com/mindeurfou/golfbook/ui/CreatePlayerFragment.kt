package com.mindeurfou.golfbook.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentCreatePlayerBinding

class CreatePlayerFragment : Fragment(R.layout.fragment_create_player) {

    private var _binding: FragmentCreatePlayerBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePlayerBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun navigateToConnectionFragment() {
//    }
}