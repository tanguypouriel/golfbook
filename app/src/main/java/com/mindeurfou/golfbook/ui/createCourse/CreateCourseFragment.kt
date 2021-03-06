package com.mindeurfou.golfbook.ui.createCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentCreateCourseBinding
import com.mindeurfou.golfbook.interactors.createCourse.CreateCourseEvent
import com.mindeurfou.golfbook.ui.customViews.HoleInputItem
import com.mindeurfou.golfbook.utils.*
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.snack
import com.mindeurfou.golfbook.utils.ErrorMessages.Companion.specific
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCourseFragment : CourseConfigFragment() {

    private val viewModel: CreateCourseViewModel by viewModels()

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
        binding.btnValidateCourse.setOnClickListener { onValidateBtnClick() }

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

        display18Holes()
    }

    private fun subscribeObservers() {
        viewModel.courseCreated.observe(viewLifecycleOwner) { observeCourseCreated(it) }
    }

    private fun observeCourseCreated(dataState: DataState<Unit>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { handleErrors(it) }
            }
            is DataState.Success -> {
                binding.progressBar.hide()
                navigateToCoursesFragment()
            }
        }
    }

    private fun handleErrors(errors: List<ErrorMessages>) {
        val sorted = ErrorMessages.sort(errors)
        sorted[specific]?.forEach {
            when (it) {
                ErrorMessages.NAME_EMPTY -> binding.courseNameInputLayout.error = it.toString()
                ErrorMessages.HOLES_UNCOMPLETED -> showUncompletedHole(it.toString())
                ErrorMessages.NAME_ALREADY_TAKEN -> binding.courseNameInputLayout.error = it.toString()
                else -> {}
            }
        }
        sorted[snack]?.let { makeSnackbar(binding.root, it) }
    }

    private fun navigateToCoursesFragment() {
        findNavController().navigate(R.id.action_createCourseFragment_to_coursesFragment)
    }

    private fun onValidateBtnClick() {
        val name = binding.courseNameEditText.text.toString()
        viewModel.setStateEvent(CreateCourseEvent.SendCourseInfoEvent(name, holes))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}