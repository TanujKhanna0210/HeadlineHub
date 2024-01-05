package com.example.headlinehub.domain.usecases.news

import com.example.headlinehub.domain.model.Article
import com.example.headlinehub.domain.repository.NewsRepository

class GetArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(url:String): Article? {
        return newsRepository.getArticle(url)
    }

}