package com.mindeurfou.golfbook.data.player.local

import android.os.Parcelable
import com.mindeurfou.golfbook.data.GBData
import com.mindeurfou.golfbook.data.player.PlayerDbEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Player(
    val id: Int,
    val name: String,
    val lastName : String,
    val username : String,
    val avatarId: Int,
    val realUser: Boolean
) : Parcelable, GBData {

    fun toDBEntity() = PlayerDbEntity(
        playerId = id,
        name = name,
        lastName = lastName,
        username = username,
        avatarId = avatarId
    )
}
