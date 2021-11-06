package com.mindeurfou.golfbook.ui.createGame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.interactors.createGame.CreateGameEvent
import com.mindeurfou.golfbook.interactors.createGame.CreateGameInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class CreateGameViewModel
@Inject constructor(
    private val createGameInteractors: CreateGameInteractors
) : ViewModel() {

    private val _createdGame: MutableLiveData<DataState<Game>> = MutableLiveData()
    val createdGame: LiveData<DataState<Game>> = _createdGame

    private val _coursesNames: MutableLiveData<DataState<List<String>>> = MutableLiveData()
    val coursesNames: LiveData<DataState<List<String>>> = _coursesNames

    fun setEventState(stateEvent: CreateGameEvent) {
        when (stateEvent) {
            is CreateGameEvent.SendGameEvent -> {
                createGameInteractors.sendGame(stateEvent.name, stateEvent.courseName).onEach {
                    _createdGame.value = it
                }.launchIn(viewModelScope)
            }
            is CreateGameEvent.GetCoursesNames -> {
                createGameInteractors.getCoursesNames().onEach {
                    _coursesNames.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
