package com.mindeurfou.golfbook.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentCoursesBinding

class CoursesFragment : Fragment(R.layout.fragment_courses) {

    private var _binding: FragmentCoursesBinding? = null
    private val binding
        get() = _binding!!

//    private val viewModel: TournamentsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        binding.floatingActionButton.setOnClickListener {
            navigateToCreateCourseFragment()
        }
    }

    private fun navigateToCreateCourseFragment() {
        findNavController().navigate(R.id.action_coursesFragment_to_createCourseFragment)
    }

    private fun subscribeObservers() {

    }
}