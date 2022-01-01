package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.remote.PatchGameNetworkEntity
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.FakeData
import com.mindeurfou.golfbook.utils.GBException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class PrepareGameInteractors
@Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl,
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
){

    fun tryStartingGame(): Flow<DataState<GameDetails>> = flow {

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(500)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.STARTING, playersReady = listOf("MindeurFou"))))
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.STARTING, playersReady = listOf("Roro", "MindeurFou"))))
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.STARTING, playersReady = listOf("Roro", "MindeurFou", "Bobby"))))
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.PENDING, playersReady = listOf("Roro", "MindeurFou", "Bobby"))))
            return@flow
        }

        // TODO
    }

    fun getGameDetails(gameId: Int) : Flow<DataState<GameDetails>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.INIT)))
            return@flow
        }

        try {
            val gameDetails = gameNetworkDataSourceImpl.getGame(gameId)
            emit(DataState.Success(gameDetails))
        } catch (e: Exception) {
            if (e is GBException && e.message == GBException.GAME_NOT_FIND_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.EMPTY_RESSOURCE)))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }

    }

    fun getCourse(courseName: String) : Flow<DataState<Course>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(500)
            emit(DataState.Success(FakeData.course()))
            return@flow
        }

        try {
            val courseDetails = courseNetworkDataSourceImpl.getCourseByName(courseName)
            emit(DataState.Success(courseDetails))
        } catch (e: Exception) {
            if (e is GBException && e.message == GBException.COURSE_NOT_FIND_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.EMPTY_RESSOURCE)))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }

    }

    fun addPlayer(
        name: String,
        lastName: String,
        username: String,
        avatarId: Int,
        gameId: Int
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
            return@flow
        }

        val errors: MutableList<ErrorMessages> = mutableListOf()

        if (name.isBlank())
            errors.add(ErrorMessages.NAME_EMPTY)
        if (lastName.isBlank())
            errors.add(ErrorMessages.LASTNAME_EMPTY)
        if (username.isBlank())
            errors.add(ErrorMessages.USERNAME_EMPTY)

        if (errors.isNotEmpty()) {
            emit(DataState.Failure(errors))
            return@flow
        }

        val patchGame = PatchGameNetworkEntity(
            name = name,
            lastName = lastName,
            username = username,
            avatarId = avatarId,
            playing = true
        )

        try {
            gameNetworkDataSourceImpl.addOrDeletePlayer(gameId, patchGame)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }

    fun acceptStart(playerName: String): Flow<DataState<Unit>> = flow {

    }

    fun rejectStart(playerName: String): Flow<DataState<Unit>> = flow {

    }
}