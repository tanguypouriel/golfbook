package com.mindeurfou.golfbook.ui.courseDetails

import androidx.lifecycle.*
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.interactors.courseDetails.CourseDetailsEvent
import com.mindeurfou.golfbook.interactors.courseDetails.CourseDetailsInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailsViewModel
@Inject constructor(
    private val courseDetailsInteractors: CourseDetailsInteractors,
    private val state: SavedStateHandle
): ViewModel(){

    private val _courseDetails: MutableLiveData<DataState<CourseDetails>> = MutableLiveData()
    val courseDetails: LiveData<DataState<CourseDetails>> = _courseDetails

    fun setStateEvent(stateEvent : CourseDetailsEvent) {
        viewModelScope.launch {
            when(stateEvent) {
                is CourseDetailsEvent.GetCourseDetails -> {
                    courseDetailsInteractors.getCourse(state.get("courseId")!!).onEach {
                        _courseDetails.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}