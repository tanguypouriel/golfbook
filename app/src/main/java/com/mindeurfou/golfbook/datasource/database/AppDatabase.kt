package com.mindeurfou.golfbook.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindeurfou.golfbook.data.player.PlayerDbEntity

@Database(entities = [PlayerDbEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
}