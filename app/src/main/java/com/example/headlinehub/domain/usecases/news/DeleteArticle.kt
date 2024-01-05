package com.example.headlinehub.domain.usecases.news

import com.example.headlinehub.domain.model.Article
import com.example.headlinehub.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article)
    }

}