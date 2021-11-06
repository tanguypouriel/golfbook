package com.mindeurfou.golfbook.interactors.createGame

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class CreateGameInteractors @Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl
) {

    fun sendGame(name: String, courseName: String, scoringSystem: String) : Flow<DataState<Game>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(FakeData.game()))
            return@flow
        }

    }

    fun getCoursesNames() : Flow<DataState<List<String>>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(200)
            val courses = FakeData.courses()
            val coursesNames = courses.map { it.name }
            emit(DataState.Success(coursesNames))
            return@flow
        }
    }
}