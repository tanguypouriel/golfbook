package com.mindeurfou.golfbook.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindeurfou.golfbook.data.player.PlayerDbEntity

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player WHERE username LIKE :username LIMIT 1")
    suspend fun getPlayerByUsername(username: String) : PlayerDbEntity

    @Query("SELECT * FROM player WHERE playerId = :playerId LIMIT 1")
    suspend fun getPlayerByPlayerId(playerId: Int) : PlayerDbEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(playerDbEntity: PlayerDbEntity)
}