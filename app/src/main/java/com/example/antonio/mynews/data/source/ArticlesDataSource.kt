package com.example.antonio.mynews.data.source

import com.example.antonio.mynews.data.Article

interface ArticlesDataSource {

    interface LoadArticlesCallback {
        fun onArticlesLoaded(articles: List<Article>)
        fun onDataNotAvailable()
    }

    fun getArticles(section: String, callback: LoadArticlesCallback)
    fun deleteArticles(id: String)
    fun saveArticles(articles: List<Article>)
    fun updateArticle(article: Article)
    fun getArchivedArticles(callback: LoadArticlesCallback)
}