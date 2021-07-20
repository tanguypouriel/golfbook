package com.mindeurfou.golfbook.data.player.local

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val lastName : String,
    val username : String,
    val drawableResourceId: Int
)
