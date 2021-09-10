package com.mindeurfou.golfbook.ui.createPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindeurfou.golfbook.interactors.CreatePlayerInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePlayerViewModel
@Inject constructor(
    private val createPlayerInteractors: CreatePlayerInteractors
) : ViewModel() {

    private val vari: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val varii: LiveData<DataState<Unit>> = vari



}