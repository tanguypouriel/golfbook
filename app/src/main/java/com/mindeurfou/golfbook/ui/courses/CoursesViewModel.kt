package com.mindeurfou.golfbook.ui.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.interactors.courses.CoursesEvent
import com.mindeurfou.golfbook.interactors.courses.CoursesInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel
@Inject constructor(
    private val coursesInteractors: CoursesInteractors
) : ViewModel(){

    private val _courses: MutableLiveData<DataState<List<Course>>> = MutableLiveData()
    val courses: LiveData<DataState<List<Course>>> = _courses

    fun setStateEvent(stateEvent : CoursesEvent) {
        when (stateEvent) {
            is CoursesEvent.GetCoursesEvent -> {
                coursesInteractors.getCourses().onEach {
                    _courses.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}