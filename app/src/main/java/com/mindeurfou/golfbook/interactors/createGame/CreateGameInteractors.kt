package com.mindeurfou.golfbook.interactors.createGame

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import java.lang.IllegalArgumentException
import javax.inject.Inject

@ExperimentalSerializationApi
class CreateGameInteractors @Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl
) {

    fun sendGame(name: String, courseName: String, scoringSystemString: String) : Flow<DataState<Int>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(1))
            return@flow
        }

        val errors: MutableList<ErrorMessages> = mutableListOf()

        if (name.isBlank())
            errors.add(ErrorMessages.NAME_EMPTY)
        if (courseName.isBlank())
            errors.add(ErrorMessages.COURSE_EMPTY)
        if (scoringSystemString.isBlank())
            errors.add(ErrorMessages.SCORING_SYSTEM_EMPTY)

        var scoringSystem: ScoringSystem? = null
        try {
            scoringSystem = ScoringSystem.valueOf(scoringSystemString)
        } catch (e: IllegalArgumentException) {}

        if (scoringSystem == null)
            errors.add(ErrorMessages.UNKNOWN_SCORING_SYSTEM)

        if (errors.isNotEmpty()) {
            emit(DataState.Failure(errors))
            return@flow
        }

        val postGame = PostGameNetworkEntity(
            name = name,
            courseName = courseName,
            scoringSystem = scoringSystem!!
        )

        try {
            val game = gameNetworkDataSourceImpl.postGame(postGame)
            if (game.equalsPostGame(postGame))
                emit(DataState.Success(game.id))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
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