package com.mindeurfou.golfbook.di

import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideConnectionInteractors() : ConnectionInteractors = ConnectionInteractors()

}