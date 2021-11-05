package com.mindeurfou.golfbook.ui.createCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentCreateCourseBinding
import com.mindeurfou.golfbook.interactors.createCourse.CreateCourseEvent
import com.mindeurfou.golfbook.ui.HoleInputItem
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCourseFragment : Fragment(R.layout.fragment_create_course){

    private var _binding: FragmentCreateCourseBinding? = null
    private val binding
        get() = _binding!!

    private var checkedButton = R.id.btn18Holes

    private lateinit var holeInputs: MutableList<HoleInputItem>
    private val holes: MutableList<Int> = MutableList(18) { 0 }

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

    private fun observeCourseCreated(dataState: DataState<Boolean>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Failure -> binding.progressBar.hide()
            is DataState.Success -> {
                binding.progressBar.hide()
                navigateToCoursesFragment()
            }
        }
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

    private fun display18Holes() {

        if (!::holeInputs.isInitialized)
            inflateHoleInputs()

        else {
            for (i in 10..18) {
                val item = HoleInputItem(requireContext(), i) { par -> holes[i] = par }
                binding.createHolesLayout.addView(item)
                holeInputs.add(item)
            }
        }

    }

    private fun display9Holes() {
        holeInputs.forEachIndexed { index, holeInputItem ->
            if (index >= 9) {
                binding.createHolesLayout.removeView(holeInputItem)
            }
        }
        holeInputs.dropLast(9)
        holes.dropLast(9)
    }

    private fun inflateHoleInputs() {
        holeInputs = mutableListOf()
        for (i in 1..18) {
            val item = HoleInputItem(requireContext(), i) { par -> holes[i-1] = par }
            binding.createHolesLayout.addView(item)
            holeInputs.add(item)
        }
    }


}