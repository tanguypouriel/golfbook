package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.player.local.Player
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

    fun tryStartingGame(gameId: Int): Flow<DataState<Unit>> = flow {

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(Unit))
            return@flow
        }

        val body = mapOf("state" to GBState.STARTING)

        try {
            gameNetworkDataSourceImpl.updateState(gameId, body)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }

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
        gameId: Int,
        players: List<Player>
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(Unit))
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

        val player = Player(
            id = 1,
            name = name,
            lastName = lastName,
            username = username,
            drawableResourceId = avatarId
        )

        val updatedList = players.toMutableList()
        updatedList.add(player)

        val body = mapOf("players" to updatedList)

        try {
            gameNetworkDataSourceImpl.updatePlayers(gameId, body)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }

    fun leaveGame(gameId: Int, playerId: Int, players: List<Player>): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(Unit))
            return@flow
        }

        val updatedList = players.toMutableList()
        val player = updatedList.find { player -> player.id == playerId }

        if (player == null) {
            emit(DataState.Failure(listOf(ErrorMessages.PLAYER_NOT_IN_GAME)))
            return@flow
        }

        updatedList.remove(player)
        val body = mapOf("players" to updatedList)

        try {
            gameNetworkDataSourceImpl.updatePlayers(gameId, body)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            if (e is GBException && e.message == GBException.GAME_NOT_FIND_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.EMPTY_RESSOURCE)))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }


    fun getPlayersReady(gameId: Int) : Flow<DataState<List<String>>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(500)
            emit(DataState.Success(listOf("MindeurFou")))
            return@flow
        }

        try {
            val playersReady = gameNetworkDataSourceImpl.getPlayersReady(gameId)
            emit(DataState.Success(playersReady))
        } catch (e: Exception) {
            if (e is GBException && e.message == GBException.GAME_NOT_FIND_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.EMPTY_RESSOURCE)))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }

    }

    fun acceptStart(gameId: Int, playerName: String, playersReady: List<String>): Flow<DataState<Unit>> = flow {

        if (playersReady.any { it == playerName }) {
            emit(DataState.Success(Unit))
            return@flow
        }

        val updatedPlayersReady = playersReady.toMutableList()
        updatedPlayersReady.add(playerName)
        try {
            gameNetworkDataSourceImpl.updatePlayersReady(gameId, updatedPlayersReady)
        } catch (e: Exception) {
            if (e is GBException && e.message == GBException.GAME_NOT_FIND_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.EMPTY_RESSOURCE)))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))

        }
    }

    fun rejectStart(gameId: Int): Flow<DataState<Unit>> = flow {
        try {
            gameNetworkDataSourceImpl.updatePlayersReady(gameId, null)
        } catch (e: Exception) {
            if (e is GBException && e.message == GBException.GAME_NOT_FIND_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.EMPTY_RESSOURCE)))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))

        }

    }
}