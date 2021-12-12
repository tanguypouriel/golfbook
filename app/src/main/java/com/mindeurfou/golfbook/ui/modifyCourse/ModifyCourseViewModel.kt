package com.mindeurfou.golfbook.ui.modifyCourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.interactors.modifyCourse.ModifyCourseEvent
import com.mindeurfou.golfbook.interactors.modifyCourse.ModifyCourseInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ModifyCourseViewModel
@Inject constructor(
    private val modifyCourseInteractors: ModifyCourseInteractors
) : ViewModel() {

    private val _modificationAccepted: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val modificationAccepted: LiveData<DataState<Boolean>> = _modificationAccepted

    fun setStateEvent(stateEvent: ModifyCourseEvent) {
        when(stateEvent) {
            is ModifyCourseEvent.SendModificationEvent -> {
                modifyCourseInteractors.sendModification(stateEvent.courseDetails).onEach {
                    _modificationAccepted.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
