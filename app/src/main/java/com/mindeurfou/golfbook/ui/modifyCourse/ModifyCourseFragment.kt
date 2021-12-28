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
import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.databinding.FragmentCreateCourseBinding
import com.mindeurfou.golfbook.interactors.modifyCourse.ModifyCourseEvent
import com.mindeurfou.golfbook.ui.createCourse.CourseConfigFragment
import com.mindeurfou.golfbook.utils.*
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
        if (navArgs.courseDetails.numberOfHoles == 9)
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

    private fun observeModificationAccepted(dataState: DataState<Unit>) {
        when (dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { handleErrors(it) }
            }
            is DataState.Success -> {
                binding.progressBar.hide()
                navigateToCourseDetailsFragment(navArgs.courseDetails.id)
            }
        }
    }

    private fun handleErrors(errors: List<ErrorMessages>) {
        val sorted = ErrorMessages.sort(errors)
        sorted[ErrorMessages.specific]?.forEach {
            when (it) {
                ErrorMessages.NAME_EMPTY -> binding.courseNameInputLayout.error = it.toString()
                ErrorMessages.HOLES_UNCOMPLETED -> showUncompletedHole(it.toString())
                else -> {}
            }
        }
        sorted[ErrorMessages.snack]?.let { makeSnackbar(binding.root, it) }
    }

    private fun onBtnValidateClick() {
        val name = binding.courseNameEditText.text.toString()
        val holesOut: MutableList<Hole> = mutableListOf()
        navArgs.courseDetails.holes.forEachIndexed { index, hole ->
            holesOut.add(hole.copy(par = holes[index]))
        }
        viewModel.setStateEvent(ModifyCourseEvent.SendModificationEvent(id, name, navArgs.courseDetails.gamesPlayed, navArgs.courseDetails.stars, navArgs.courseDetails.createdAt, holesOut))
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