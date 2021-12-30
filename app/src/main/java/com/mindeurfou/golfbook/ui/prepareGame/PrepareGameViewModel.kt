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
    private val socketBuilder: WebSocketBuilder,
    private val gameWebSocketListener: GameWebSocketListener,
    sharedPreferences: SharedPreferences,
    state: SavedStateHandle
) : ViewModel() {

    private val _gameDetails: MutableLiveData<DataState<GameDetails>> = MutableLiveData()
    val gameDetails: LiveData<DataState<GameDetails>> = _gameDetails

    private val _course: MutableLiveData<DataState<Course>> = MutableLiveData()
    val course: LiveData<DataState<Course>> = _course

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

            }
            is PrepareGameEvent.RejectGameStart -> {

            }
            is PrepareGameEvent.GetCourseEvent -> {
                prepareGameInteractors.getCourse(stateEvent.courseId).onEach {
                    _course.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.ObserveGameEvent -> {
                val socket = socketBuilder.openSocket(gameWebSocketListener)
            }
        }
    }
}
