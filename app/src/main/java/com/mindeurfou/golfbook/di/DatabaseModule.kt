package com.mindeurfou.golfbook.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.mindeurfou.golfbook.datasource.database.AppDatabase
import com.mindeurfou.golfbook.datasource.database.PlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        databaseBuilder(context, AppDatabase::class.java, "GB_DB").build()

    @Provides
    fun providePlayerDao(appDatabase: AppDatabase): PlayerDao =
        appDatabase.playerDao()
}