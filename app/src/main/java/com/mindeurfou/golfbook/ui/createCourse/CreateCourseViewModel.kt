package com.mindeurfou.golfbook.ui.createCourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.interactors.createCourse.CreateCourseEvent
import com.mindeurfou.golfbook.interactors.createCourse.CreateCourseInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel
@Inject constructor(
    private val createCourseInteractors: CreateCourseInteractors
) : ViewModel(){

    private val _courseCreated: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val courseCreated: LiveData<DataState<Boolean>> = _courseCreated

    fun setStateEvent(stateEvent : CreateCourseEvent) {
        when(stateEvent) {
            is CreateCourseEvent.SendCourseInfoEvent -> {
                createCourseInteractors.sendCourseInfo(stateEvent.name, stateEvent.holes).onEach {
                    _courseCreated.value = it
                }.launchIn(viewModelScope)
            }

        }
    }
}