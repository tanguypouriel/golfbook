package com.mindeurfou.golfbook.ui

import androidx.lifecycle.ViewModel
import com.mindeurfou.golfbook.datasource.network.GameWebSocketListener
import com.mindeurfou.golfbook.datasource.network.WebSocketBuilder
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

    fun observeGame(gameId: Int, gameListener: GameListener) {
        if (gameSocket == null)
            webSocketBuilder.openSocket(gameWebSocketListener)

        gameWebSocketListener.gameListener = gameListener
    }
}