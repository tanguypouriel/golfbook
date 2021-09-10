package com.mindeurfou.golfbook.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UtilsModule {

    @Provides
    fun provideSharedPreferences(
        application: Application
    ): SharedPreferences = application.getSharedPreferences("CREDENTIALS_FILE", Context.MODE_PRIVATE)
}