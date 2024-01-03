package com.example.headlinehub.domain.usecases.news

import com.example.headlinehub.data.local.NewsDao
import com.example.headlinehub.domain.model.Article

class DeleteArticle(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(article: Article) {
        newsDao.delete(article)
    }

}