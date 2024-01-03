package com.example.headlinehub.di

import android.app.Application
import com.example.headlinehub.data.manager.LocalUserManagerImpl
import com.example.headlinehub.data.remote.NewsApi
import com.example.headlinehub.data.repository.NewsRepositoryImpl
import com.example.headlinehub.domain.manager.LocalUserManager
import com.example.headlinehub.domain.repository.NewsRepository
import com.example.headlinehub.domain.usecases.app_entry.AppEntryUseCases
import com.example.headlinehub.domain.usecases.app_entry.ReadAppEntry
import com.example.headlinehub.domain.usecases.app_entry.SaveAppEntry
import com.example.headlinehub.domain.usecases.news.GetNews
import com.example.headlinehub.domain.usecases.news.NewsUseCases
import com.example.headlinehub.domain.usecases.news.SearchNews
import com.example.headlinehub.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun providesNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(
        newsApi: NewsApi
    ): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun providesNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository)
        )
    }

}