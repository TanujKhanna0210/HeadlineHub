package com.example.headlinehub.domain.usecases.news

import com.example.headlinehub.domain.model.Article
import com.example.headlinehub.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.getArticles()
    }

}