package com.mindeurfou.golfbook.datasource.network

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.player.PlayerApiService
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSource
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
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
class PlayerNetworkDataSourceTest : BaseApiTest() {

    private val playerNetworkDataSource: PlayerNetworkDataSource = PlayerNetworkDataSourceImpl(getApiService(PlayerApiService::class.java))

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPlayer() = runBlocking {
        mockWebServer.enqueueResponse("player.json", 200)

        val result = playerNetworkDataSource.getPlayer(1)
        val expected = Player(1, "Tanguy", "Pouriel", "MindeurFou", 1)
        assertEquals(expected, result)
    }

}