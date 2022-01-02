package com.mindeurfou.golfbook.di

import com.mindeurfou.golfbook.datasource.network.websocket.RetrofitBuilder
import com.mindeurfou.golfbook.datasource.network.course.CourseApiService
import com.mindeurfou.golfbook.datasource.network.game.GameApiService
import com.mindeurfou.golfbook.datasource.network.player.PlayerApiService
import com.mindeurfou.golfbook.datasource.network.tournament.TournamentApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    fun providePlayerApiService(retrofitBuilder: RetrofitBuilder): PlayerApiService =
        retrofitBuilder.retrofit.create(PlayerApiService::class.java)

    @Provides
    fun provideCourseApiService(retrofitBuilder: RetrofitBuilder): CourseApiService =
        retrofitBuilder.retrofit.create(CourseApiService::class.java)

    @Provides
    fun provideGameApiService(retrofitBuilder: RetrofitBuilder): GameApiService =
        retrofitBuilder.retrofit.create(GameApiService::class.java)

    @Provides
    fun provideTournamentApiService(retrofitBuilder: RetrofitBuilder): TournamentApiService =
        retrofitBuilder.retrofit.create(TournamentApiService::class.java)

}