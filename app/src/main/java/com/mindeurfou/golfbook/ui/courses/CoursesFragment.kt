package com.mindeurfou.golfbook.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.MainActivity
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.databinding.FragmentCoursesBinding
import com.mindeurfou.golfbook.interactors.courses.CoursesEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : Fragment(R.layout.fragment_courses) {

    private var _binding: FragmentCoursesBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CoursesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(CoursesEvent.GetCoursesEvent)

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

    private fun subscribeObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { observeCourses(it) }
    }

    private fun observeCourses(dataState: DataState<List<Course>>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()
                binding.recyclerCourses.adapter = CourseAdapter(dataState.data) {
                    navigateToCourseDetails(it.id)
                }
            }
        }
    }

    private fun navigateToCreateCourseFragment() {
        findNavController().navigate(R.id.action_coursesFragment_to_createCourseFragment)
    }

    private fun navigateToCourseDetails(courseId: Int) {
        val action = CoursesFragmentDirections.actionCoursesFragmentToCourseDetailsFragment(courseId)
        findNavController().navigate(action)
    }
}