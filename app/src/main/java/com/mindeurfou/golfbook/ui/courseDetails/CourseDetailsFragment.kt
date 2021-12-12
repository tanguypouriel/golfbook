package com.mindeurfou.golfbook.ui.courseDetails

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    private var startInstant: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseDetailsBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(CourseDetailsEvent.GetCourseDetails)

        showProgressBar()
        startInstant = SystemClock.elapsedRealtime()

        return binding.root
    }

    private fun setupUI() {

    }

    private fun subscribeObservers() {
        viewModel.courseDetails.observe(viewLifecycleOwner) { observeCourseDetails(it) }
    }

    private fun observeCourseDetails(dataState: DataState<CourseDetails>) {
        when (dataState) {
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
                showData()

                binding.modifyCourseButton.setOnClickListener { navigateToModifyCourseFragment(courseDetails) }
            }
            is DataState.Loading -> {}
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

    private fun showProgressBar() {
        binding.progressBar.show()
        AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_show_animator).apply {
            setTarget(binding.progressBar)
            startDelay = 1000
            start()
        }
    }

    private fun showData() {
        val now = SystemClock.elapsedRealtime()
        val delay = if (now - startInstant!! < 1000)
            1000L
        else
            0L
        val set: MutableList<Animator> = mutableListOf()

        val animatedViews : List<View> = listOf(
            binding.numberOfHoles,
            binding.title,
            binding.courseParView,
            binding.coursePar,
            binding.numberOfHoles,
            binding.gamesPlayed,
            binding.createdAt,
            binding.courseStaringLayout,
            binding.modifyCourseButton
        )

        animatedViews.forEach {
            AnimatorInflater.loadAnimator(requireContext(), R.animator.alpha_show_animator).apply {
                setTarget(it)
                startDelay = delay
                set.add(this)
            }
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(set)
        animatorSet.start()
    }

    private fun navigateToModifyCourseFragment(courseDetails: CourseDetails) {
        val action = CourseDetailsFragmentDirections.actionCourseDetailsFragmentToModifyCourseFragment(courseDetails)
        findNavController().navigate(action)
    }
}