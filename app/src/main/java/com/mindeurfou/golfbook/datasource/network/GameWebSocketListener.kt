package com.mindeurfou.golfbook.datasource.network

import com.mindeurfou.golfbook.ui.GameListener
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class GameWebSocketListener
@Inject constructor() : WebSocketListener() {

    var gameListener: GameListener? = null

    private val gameDetailsTag: Byte = 0x01
    private val scoreTag: Byte = 0x02

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        gameListener?.let { listener ->
            when(bytes[0]) {
                gameDetailsTag -> listener.onGameDetailsNotification()
                scoreTag -> listener.onScoreNotification()
                else -> {}
            }
        }
    }
}