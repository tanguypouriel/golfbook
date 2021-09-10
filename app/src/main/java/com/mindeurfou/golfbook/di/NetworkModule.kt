package com.mindeurfou.golfbook.di

import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder
import com.mindeurfou.golfbook.datasource.network.course.CourseApiService
import com.mindeurfou.golfbook.datasource.network.game.GameApiService
import com.mindeurfou.golfbook.datasource.network.player.PlayerApiService
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.datasource.network.tournament.TournamentApiService
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    fun providePlayerApiService(): PlayerApiService =
        RetrofitBuilder.retrofit.create(PlayerApiService::class.java)

    @Provides
    fun provideCourseApiService(): CourseApiService =
        RetrofitBuilder.retrofit.create(CourseApiService::class.java)

    @Provides
    fun provideGameApiService(): GameApiService =
        RetrofitBuilder.retrofit.create(GameApiService::class.java)

    @Provides
    fun provideTournamentApiService(): TournamentApiService =
        RetrofitBuilder.retrofit.create(TournamentApiService::class.java)

}