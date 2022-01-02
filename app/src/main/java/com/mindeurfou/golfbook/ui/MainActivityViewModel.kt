package com.mindeurfou.golfbook.ui

import androidx.lifecycle.ViewModel
import com.mindeurfou.golfbook.datasource.network.websocket.GameWebSocketListener
import com.mindeurfou.golfbook.datasource.network.websocket.WebSocketBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.WebSocket
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val webSocketBuilder: WebSocketBuilder,
    private val gameWebSocketListener: GameWebSocketListener
) : ViewModel() {

    private var gameSocket: WebSocket? = null

    fun openGameSocket(gameId: Int, gameListener: GameListener) {
        gameSocket = webSocketBuilder.openSocket(gameWebSocketListener, gameId)
        gameWebSocketListener.gameListener = gameListener
    }

    fun observeGameSocket(gameListener: GameListener) {
        gameWebSocketListener.gameListener = gameListener
    }
}