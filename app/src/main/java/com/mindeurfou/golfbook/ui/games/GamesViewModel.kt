package com.mindeurfou.golfbook.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.interactors.games.GamesEvent
import com.mindeurfou.golfbook.interactors.games.GamesInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@HiltViewModel
@ExperimentalSerializationApi
class GamesViewModel
@Inject constructor(
    private val gamesInteractors: GamesInteractors
) : ViewModel(){

    private val _games: MutableLiveData<DataState<List<Game>>> = MutableLiveData()
    val games: LiveData<DataState<List<Game>>> = _games

    fun setStateEvent(gamesEvent: GamesEvent) {
        when(gamesEvent) {
            is GamesEvent.GetGamesEvent -> {
                gamesInteractors.getGames().onEach {
                    _games.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}