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

    private val _modificationAccepted: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val modificationAccepted: LiveData<DataState<Unit>> = _modificationAccepted

    fun setStateEvent(stateEvent: ModifyCourseEvent) {
        when(stateEvent) {
            is ModifyCourseEvent.SendModificationEvent -> {
                modifyCourseInteractors.sendModification(
                    id = stateEvent.id,
                    name = stateEvent.name,
                    gamesPlayed = stateEvent.gamesPlayed,
                    stars = stateEvent.stars,
                    createdAt = stateEvent.createdAt,
                    holes = stateEvent.holes
                ).onEach {
                    _modificationAccepted.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
