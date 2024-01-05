package com.example.headlinehub.domain.usecases.news

import com.example.headlinehub.domain.model.Article
import com.example.headlinehub.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.upsertArticle(article)
    }

}