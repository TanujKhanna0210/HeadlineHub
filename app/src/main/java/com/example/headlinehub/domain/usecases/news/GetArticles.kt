package com.example.headlinehub.domain.usecases.news

import com.example.headlinehub.data.local.NewsDao
import com.example.headlinehub.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsDao: NewsDao
) {

    operator fun invoke(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

}