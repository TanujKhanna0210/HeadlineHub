package com.example.headlinehub.di

import android.app.Application
import androidx.room.Room
import com.example.headlinehub.data.local.NewsDao
import com.example.headlinehub.data.local.NewsDatabase
import com.example.headlinehub.data.local.NewsTypeConverter
import com.example.headlinehub.data.manager.LocalUserManagerImpl
import com.example.headlinehub.data.remote.NewsApi
import com.example.headlinehub.data.repository.NewsRepositoryImpl
import com.example.headlinehub.domain.manager.LocalUserManager
import com.example.headlinehub.domain.repository.NewsRepository
import com.example.headlinehub.domain.usecases.app_entry.AppEntryUseCases
import com.example.headlinehub.domain.usecases.app_entry.ReadAppEntry
import com.example.headlinehub.domain.usecases.app_entry.SaveAppEntry
import com.example.headlinehub.domain.usecases.news.DeleteArticle
import com.example.headlinehub.domain.usecases.news.GetArticle
import com.example.headlinehub.domain.usecases.news.GetArticles
import com.example.headlinehub.domain.usecases.news.GetNews
import com.example.headlinehub.domain.usecases.news.NewsUseCases
import com.example.headlinehub.domain.usecases.news.SearchNews
import com.example.headlinehub.domain.usecases.news.UpsertArticle
import com.example.headlinehub.util.Constants.BASE_URL
import com.example.headlinehub.util.Constants.NEWS_DATABASE_NAME
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
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)

    @Provides
    @Singleton
    fun providesNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            getArticles = GetArticles(newsRepository),
            getArticle = GetArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun providesNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}