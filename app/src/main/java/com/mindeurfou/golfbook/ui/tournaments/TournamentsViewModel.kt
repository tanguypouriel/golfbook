package com.mindeurfou.golfbook.ui.tournaments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.interactors.tournaments.TournamentsInteractors
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.state.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@HiltViewModel
@ExperimentalSerializationApi
class TournamentsViewModel
@Inject constructor(
    private val tournamentsInteractors: TournamentsInteractors
): ViewModel() {

    private val _tournaments : MutableLiveData<DataState<List<Tournament>>> = MutableLiveData()
    val tournaments : LiveData<DataState<List<Tournament>>> = _tournaments


    fun setStateEvent(stateEvent: StateEvent) {
        viewModelScope.launch {
            when(stateEvent) {
                is TournamentsEvent.GetTournamentsEvent -> {
                    tournamentsInteractors.getTournaments().onEach {
                        _tournaments.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}