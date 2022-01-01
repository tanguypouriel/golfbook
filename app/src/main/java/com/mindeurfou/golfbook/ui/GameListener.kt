package com.mindeurfou.golfbook.ui

interface GameListener {
    fun onGameDetailsNotification()
    fun onScoreNotification()
    fun onPlayersReadyNotification()
}