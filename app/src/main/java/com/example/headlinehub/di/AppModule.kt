package com.example.headlinehub.di

import android.app.Application
import com.example.headlinehub.data.manager.LocalUserManagerImpl
import com.example.headlinehub.domain.manager.LocalUserManager
import com.example.headlinehub.domain.usecases.AppEntryUseCases
import com.example.headlinehub.domain.usecases.ReadAppEntry
import com.example.headlinehub.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun providesAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

}