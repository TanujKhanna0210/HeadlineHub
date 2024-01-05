package com.example.headlinehub.domain.usecases.news

import com.example.headlinehub.data.local.NewsDao
import com.example.headlinehub.domain.model.Article

class GetArticle(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(url:String): Article? {
        return newsDao.getArticle(url)
    }

}