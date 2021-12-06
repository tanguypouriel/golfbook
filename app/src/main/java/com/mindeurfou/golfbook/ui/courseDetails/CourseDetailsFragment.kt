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
        viewModel.courseDetails.observe(viewLifecycleOwner) { observeCourseDetails(it) }
    }

    private fun observeCourseDetails(dataState: DataState<CourseDetails>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()

                val courseDetails = dataState.data

                binding.title.text = courseDetails.name
                binding.coursePar.text =  getString(R.string.coursePar, courseDetails.par)
                binding.numberOfHoles.text = getString(R.string.numberOfHoles, courseDetails.numberOfHOles)
                binding.createdAt.text = getString(R.string.createdAt, courseDetails.createdAt.toString())
                binding.gamesPlayed.text = getString(R.string.gamesPlayed, courseDetails.gamesPlayed)
                bindStars(courseDetails.stars)
                binding.courseParView.par = courseDetails.getParList()
                binding.courseParView.visibility = View.VISIBLE
            }
        }
    }
    private fun bindStars(stars: Int) {
        for (i in 1..stars) {
            when (i) {
                1 -> binding.starBall1.setImageResource(R.drawable.ic_ball_full)
                2 -> binding.starBall2.setImageResource(R.drawable.ic_ball_full)
                3 -> binding.starBall3.setImageResource(R.drawable.ic_ball_full)
                4 -> binding.starBall4.setImageResource(R.drawable.ic_ball_full)
                5 -> binding.starBall5.setImageResource(R.drawable.ic_ball_full)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}