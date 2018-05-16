package com.example.antonio.mynews.data.source.local

import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.source.ArticlesDataSource
import com.example.antonio.mynews.utils.AppExecutors

class ArticlesLocalDataSource(
        private val appExecutors: AppExecutors,
        private val articlesDao: ArticlesDao
) : ArticlesDataSource {

    override fun getArticles(section: String, callback: ArticlesDataSource.LoadArticlesCallback) {
        appExecutors.diskIO.execute {
            val articles = articlesDao.loadArticlesBySection(section)

            appExecutors.mainThread.execute {
                if (articles.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onArticlesLoaded(articles)
                }
            }
        }
    }

    override fun saveArticles(articles: List<Article>) {
        appExecutors.diskIO.execute { articlesDao.insertArticles(articles) }
    }

    override fun updateArticle(id: String, isArchived: Boolean) {
        // SQLite does not have a boolean data type so Room maps it to an INTEGER column, mapping
        // true to 1 and false to 0.
        val isArchivedAsInt =
                when (isArchived) {
                    true -> 1
                    false -> 0
                }
        appExecutors.diskIO.execute { articlesDao.updateArticle(id, isArchivedAsInt) }
    }

    override fun deleteArticles(id: String) {
        appExecutors.diskIO.execute { articlesDao.deleteArticleById(id) }
    }

    override fun getArchivedArticles(callback: ArticlesDataSource.LoadArticlesCallback) {
        appExecutors.diskIO.execute {
            val archivedArticles = articlesDao.loadArchivedArticles()

            appExecutors.mainThread.execute {
                if (archivedArticles.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onArticlesLoaded(archivedArticles)
                }
            }
        }
    }
}