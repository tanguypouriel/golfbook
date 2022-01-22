package com.mindeurfou.golfbook.ui.prepareGame

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.PLAYER_ID_KEY
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.USERNAME_KEY
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameEvent
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameInteractors
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
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

    private val _playersReady: MutableLiveData<DataState<List<String>>> = MutableLiveData()
    val playersReady: LiveData<DataState<List<String>>> = _playersReady

    private val _status: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val status: MutableLiveData<DataState<Unit>> = _status

    private val _playerAccepted: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val playerAccepted: LiveData<DataState<Unit>> = _playerAccepted

    private val _existingPlayers: MutableLiveData<DataState<List<Player>>> = MutableLiveData()
    val existingPlayers: LiveData<DataState<List<Player>>> = _existingPlayers

    val selfName: String? = sharedPreferences.getString(USERNAME_KEY, null)
    private val selfId: Int = sharedPreferences.getInt(PLAYER_ID_KEY, 0)

    private val gameId: Int = state.get("gameId")!!

    fun setStateEvent(stateEvent: PrepareGameEvent) {
        when(stateEvent) {
            is PrepareGameEvent.GetGameDetailsEvent -> {
                prepareGameInteractors.getGameDetails(gameId).onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.TryStartGameEvent -> {
                prepareGameInteractors.tryStartingGame(gameId).onEach {
                    _status.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.CheckPlayerReadyEvent -> {
                prepareGameInteractors.getPlayersReady(gameId).onEach {
                    _playersReady.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.AcceptGameStartEvent -> {
                if (playersReady.value !is DataState.Success) {
                    _status.value = DataState.Failure(listOf(ErrorMessages.INTERNAL_ERROR))
                    return
                }
                val playersReadyList = (playersReady.value as DataState.Success<List<String>>).data

                prepareGameInteractors.acceptStart(gameId, selfName!!, playersReadyList).onEach {
                    _status.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.RejectGameStartEvent -> {
                prepareGameInteractors.rejectStart(gameId) .onEach {
                    _status.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.GetCourseEvent -> {
                prepareGameInteractors.getCourse(stateEvent.courseName).onEach {
                    _course.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.AddPlayerEvent -> {
                if (gameDetails.value !is DataState.Success) {
                    _status.value = DataState.Failure(listOf(ErrorMessages.INTERNAL_ERROR))
                    return
                }
                val gameDetails = (gameDetails.value as DataState.Success<GameDetails>).data

                prepareGameInteractors.addPlayer(stateEvent.player, gameId, gameDetails.players).onEach {
                    _playerAccepted.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.CreateAndAddPlayerEvent -> {
                if (gameDetails.value !is DataState.Success) {
                    _status.value = DataState.Failure(listOf(ErrorMessages.INTERNAL_ERROR))
                    return
                }
                val gameDetails = (gameDetails.value as DataState.Success<GameDetails>).data

                prepareGameInteractors.createAndAddPlayer(
                    stateEvent.name,
                    stateEvent.lastName,
                    stateEvent.username,
                    stateEvent.avatarId,
                    gameId,
                    gameDetails.players
                ).onEach {
                    _playerAccepted.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.LeaveGameEvent -> {
                if (selfId == 0) {
                    _status.value = DataState.Failure(listOf(ErrorMessages.INTERNAL_ERROR))
                    return
                }

                if (gameDetails.value !is DataState.Success) {
                    _status.value = DataState.Failure(listOf(ErrorMessages.INTERNAL_ERROR))
                    return
                }
                val gameDetails = (gameDetails.value as DataState.Success<GameDetails>).data

                prepareGameInteractors.leaveGame(gameId, selfId, gameDetails.players).onEach {
                    _status.value = it
                }.launchIn(viewModelScope)

            }
            is PrepareGameEvent.GetPlayersEvent -> {
                prepareGameInteractors.getExistingPlayers().onEach {
                    _existingPlayers.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
