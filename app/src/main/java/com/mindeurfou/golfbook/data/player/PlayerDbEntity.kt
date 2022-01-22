package com.mindeurfou.golfbook.data.player

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mindeurfou.golfbook.data.player.local.Player

@Entity(tableName = "player")
data class PlayerDbEntity(

    @PrimaryKey(autoGenerate = true)
    val dbId: Int? = null,
    val playerId: Int,
    val name: String,
    val lastName : String,
    val username : String,
    val avatarId: Int
) {

    fun toPlayer() = Player(
        id = playerId,
        name = name,
        lastName = lastName,
        username = username,
        avatarId = avatarId,
        realUser = true
    )
}