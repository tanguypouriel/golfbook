package com.mindeurfou.golfbook.data.player.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Player(
    val id: Int,
    val name: String,
    val lastName : String,
    val username : String,
    val drawableResourceId: Int
) : Parcelable
