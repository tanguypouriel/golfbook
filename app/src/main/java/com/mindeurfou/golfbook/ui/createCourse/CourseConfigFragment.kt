package com.mindeurfou.golfbook.ui.createCourse

import androidx.fragment.app.Fragment
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentCreateCourseBinding
import com.mindeurfou.golfbook.ui.customViews.HoleInputItem
import com.mindeurfou.golfbook.utils.ErrorMessages

abstract class CourseConfigFragment : Fragment(R.layout.fragment_create_course) {

    protected var _binding: FragmentCreateCourseBinding? = null
    protected val binding
        get() = _binding!!

    protected var checkedButton = R.id.btn18Holes

    private lateinit var holeInputs: MutableList<HoleInputItem>
    protected var holes: MutableList<Int> = MutableList(18) { 0 }

    protected fun display18Holes() {

        if (!::holeInputs.isInitialized)
            inflateHoleInputs()

        else {
            for (i in 10..18) {
                val item = HoleInputItem(requireContext(), i, holes[i-1]) { par -> holes[i-1] = par }
                binding.createHolesLayout.addView(item)
                holeInputs.add(item)
            }
        }

    }

    protected fun display9Holes() {
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
            val item = HoleInputItem(requireContext(), i, holes[i-1]) { par -> holes[i-1] = par }
            binding.createHolesLayout.addView(item)
            holeInputs.add(item)
        }
    }

    protected fun showUncompletedHole(errorMessage: String) {
        holeInputs.forEach {
            if (it.isEmpty())
                it.displayError(errorMessage)
        }
    }
}