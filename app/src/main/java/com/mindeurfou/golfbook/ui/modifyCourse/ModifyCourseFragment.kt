package com.mindeurfou.golfbook.ui.modifyCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.databinding.FragmentCreateCourseBinding
import com.mindeurfou.golfbook.interactors.modifyCourse.ModifyCourseEvent
import com.mindeurfou.golfbook.ui.createCourse.CourseConfigFragment
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyCourseFragment : CourseConfigFragment() {

    private val navArgs: ModifyCourseFragmentArgs by navArgs()

    private val viewModel: ModifyCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCourseBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        return binding.root
    }

    private fun setupUI() {
        binding.createCourseTitle.text = getString(R.string.modifyCourse)
        binding.courseNameEditText.setText(navArgs.courseDetails.name)

        binding.toggleButtonNbHoles.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                if (checkedId == R.id.btn18Holes && checkedButton != R.id.btn18Holes) {
                    checkedButton = R.id.btn18Holes
                    display18Holes()
                }
                else if (checkedId == R.id.btn9Holes && checkedButton != R.id.btn9Holes) {
                    checkedButton = R.id.btn9Holes
                    display9Holes()
                }
            }
        }
        if (navArgs.courseDetails.numberOfHOles == 9)
            binding.toggleButtonNbHoles.check(R.id.btn9Holes)
        else
            binding.toggleButtonNbHoles.check(R.id.btn18Holes)

        binding.btnValidateCourse.setOnClickListener { onBtnValidateClick() }

        holes = navArgs.courseDetails.holes.map { it.par }.toMutableList()
        display18Holes()
    }

    private fun subscribeObservers() {
        viewModel.modificationAccepted.observe(viewLifecycleOwner) { observeModificationAccepted(it) }
    }

    private fun observeModificationAccepted(dataState: DataState<Boolean>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> {
                binding.progressBar.hide()
            }
            is DataState.Success -> {
                binding.progressBar.hide()
                navigateToCourseDetailsFragment(navArgs.courseDetails.id)
            }
        }
    }

    private fun onBtnValidateClick() {
        val courseDetails = CourseDetails(
            id = navArgs.courseDetails.id,
            name = binding.courseNameEditText.text.toString(),
            numberOfHOles = 18, // TODO
            par = 72, // TODO
            gamesPlayed = navArgs.courseDetails.gamesPlayed,
            stars = 4, // TODO
            createdAt = navArgs.courseDetails.createdAt,
            holes = navArgs.courseDetails.holes // TODO
        )
        viewModel.setStateEvent(ModifyCourseEvent.SendModificationEvent(courseDetails))
    }

    private fun navigateToCourseDetailsFragment(courseId: Int) {
        val action = ModifyCourseFragmentDirections.actionModifyCourseFragmentToCourseDetailsFragment(courseId = courseId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}