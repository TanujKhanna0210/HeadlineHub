package com.example.headlinehub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.headlinehub.data.local.NewsDao
import com.example.headlinehub.data.remote.NewsApi
import com.example.headlinehub.data.remote.NewsPagingSource
import com.example.headlinehub.data.remote.SearchNewsPagingSource
import com.example.headlinehub.domain.model.Article
import com.example.headlinehub.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {

    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        val pager = Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(",")
                )
            }
        )
        return pager.flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        val pager = Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(",")
                )
            }
        )
        return pager.flow
    }

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    override suspend fun getArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }
}