package com.mindeurfou.golfbook.datasource.network

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.game.GameApiService
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSource
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameNetworkDataSourceTest : BaseApiTest(){

    private val gameNetworkDataSource: GameNetworkDataSource = GameNetworkDataSourceImpl(getApiService(GameApiService::class.java))

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getGame() = runBlocking {
        mockWebServer.enqueueResponse("gameDetails.json", 200)

        val result = gameNetworkDataSource.getGame(1)
        val expected = GameDetails(
            1,
            GBState.WAITING,
            "Parcours du test",
            1,
            1,
            listOf(Player(1, "Tanguy", "Pouriel", "MindeurFou", 1)),
            ScoreBook(mapOf("MindeurFou" to listOf(null, null, null, null, null, null, null, null, null)))
        )
        assertEquals(expected, result)
    }

    @Test
    fun getScoreBook() = runBlocking {
        mockWebServer.enqueueResponse("scorebook.json", 200)

        val result = gameNetworkDataSource.getScoreBook(1)
        val expected = ScoreBook(mapOf("MindeurFou" to listOf(null, null, null, null, null, null, null, null, null)))
        assertEquals(expected, result)
    }
}