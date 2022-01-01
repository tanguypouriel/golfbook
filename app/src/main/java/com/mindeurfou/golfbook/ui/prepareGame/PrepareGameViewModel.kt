package com.mindeurfou.golfbook.ui.prepareGame

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.datasource.network.GameWebSocketListener
import com.mindeurfou.golfbook.datasource.network.WebSocketBuilder
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.USERNAME_KEY
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameEvent
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject


@ExperimentalSerializationApi
@HiltViewModel
class PrepareGameViewModel
@Inject constructor(
    private val prepareGameInteractors: PrepareGameInteractors,
    sharedPreferences: SharedPreferences,
    state: SavedStateHandle
) : ViewModel() {

    private val _gameDetails: MutableLiveData<DataState<GameDetails>> = MutableLiveData()
    val gameDetails: LiveData<DataState<GameDetails>> = _gameDetails

    private val _course: MutableLiveData<DataState<Course>> = MutableLiveData()
    val course: LiveData<DataState<Course>> = _course

    private val _playerAccepted: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val playerAccepted: LiveData<DataState<Unit>> = _playerAccepted

    private val _acceptStartStatus: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val acceptStartStatus: MutableLiveData<DataState<Unit>> = _acceptStartStatus

    private val _rejectStartStatus: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val rejectStartStatus: MutableLiveData<DataState<Unit>> = _rejectStartStatus

    val selfName: String? = sharedPreferences.getString(USERNAME_KEY, null)

    private val gameId: Int = state.get("gameId")!!

    fun setStateEvent(stateEvent: PrepareGameEvent) {
        when(stateEvent) {
            is PrepareGameEvent.CheckPlayerReady -> {
                prepareGameInteractors.tryStartingGame().onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.GetGameDetailsEvent -> {
                prepareGameInteractors.getGameDetails(gameId).onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.AcceptGameStart -> {
                prepareGameInteractors.acceptStart(selfName!!).onEach {
                    _acceptStartStatus.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.RejectGameStart -> {
                prepareGameInteractors.rejectStart(selfName!!) .onEach {
                    _rejectStartStatus.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.GetCourseEvent -> {
                prepareGameInteractors.getCourse(stateEvent.courseName).onEach {
                    _course.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.AddPlayer -> {
                prepareGameInteractors.addPlayer(
                    stateEvent.name,
                    stateEvent.lastName,
                    stateEvent.username,
                    stateEvent.avatarId,
                    gameId
                ).onEach {
                    _playerAccepted.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
