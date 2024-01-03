package com.example.headlinehub.presentation.bookmark

import com.example.headlinehub.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)