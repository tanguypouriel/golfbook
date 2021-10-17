package com.mindeurfou.golfbook.ui.courseDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.databinding.FragmentCourseDetailsBinding
import com.mindeurfou.golfbook.interactors.courseDetails.CourseDetailsEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailsFragment : Fragment(R.layout.fragment_course_details){

    private var _binding: FragmentCourseDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CourseDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseDetailsBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(CourseDetailsEvent.GetCourseDetails)

        return binding.root
    }

    private fun setupUI() {

    }

    private fun subscribeObservers() {
        viewModel.courseDetails.observe(viewLifecycleOwner) { obvserveCourseDetails(it) }
    }

    private fun obvserveCourseDetails(dataState: DataState<CourseDetails>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()
                // TODO
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}