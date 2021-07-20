package com.mindeurfou.golfbook.data.hole.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostHoleNetworkEntity(
    val holeNumber: Int,
    val par: Int
)
