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
    fun updateArticle(id:String, isArchived:Boolean)
    fun getArchivedArticles(callback: LoadArticlesCallback)
}