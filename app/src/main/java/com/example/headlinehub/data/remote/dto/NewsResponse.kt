package com.example.headlinehub.data.remote.dto

import com.example.headlinehub.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)