package com.example.antonio.mynews.data.source

import com.example.antonio.mynews.data.Article
import java.util.*

class ArticlesRepository(
        private val articlesRemoteDataSource: ArticlesDataSource,
        private val articlesLocalDataSource: ArticlesDataSource
) : ArticlesDataSource {

    var cachedArticles: LinkedHashMap<String, Article> = LinkedHashMap()

    private var cacheIsDirty = false

    override fun getArticles(section: String, callback: ArticlesDataSource.LoadArticlesCallback) {
        // Respond immediately with cache if available and not dirty
        if (cachedArticles.isNotEmpty() && !cacheIsDirty) {
            callback.onArticlesLoaded(ArrayList(cachedArticles.values))
            return
        }

        if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getArticlesFromRemoteDataSource(section, callback)
        } else {
            // Query the local storage if available. If not, query the network.
            articlesLocalDataSource.getArticles(section, object : ArticlesDataSource.LoadArticlesCallback {
                override fun onArticlesLoaded(articles: List<Article>) {
                    refreshCache(articles)
                    callback.onArticlesLoaded(ArrayList(cachedArticles.values))
                }

                override fun onDataNotAvailable() {
                    getArticlesFromRemoteDataSource(section, callback)
                }
            })
        }
    }

    override fun saveArticles(articles: List<Article>) {
        articlesLocalDataSource.saveArticles(articles)
    }

    override fun updateArticle(id: String, isArchived:Boolean) {
        articlesLocalDataSource.updateArticle(id, isArchived)
    }

    override fun deleteArticles(id: String) {
        articlesLocalDataSource.deleteArticles(id)
    }

    fun getArticlesFromRemoteDataSource(section: String, callback: ArticlesDataSource.LoadArticlesCallback) {
        articlesRemoteDataSource.getArticles(section, object : ArticlesDataSource.LoadArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>) {
                refreshCache(articles)
                refreshLocalDataSource(articles)
                callback.onArticlesLoaded(ArrayList(articles))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun getArchivedArticles(callback: ArticlesDataSource.LoadArticlesCallback) {
        articlesLocalDataSource.getArchivedArticles(object : ArticlesDataSource.LoadArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>) {
                callback.onArticlesLoaded(articles)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(articles: List<Article>) {
        cachedArticles.clear()
        articles.forEach { cache(it) }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(articles: List<Article>) {
        articles.forEach { articlesLocalDataSource.deleteArticles(it.id) }
        articlesLocalDataSource.saveArticles(articles)
    }

    private fun cache(article: Article) {
        val cachedArticle =
                Article(
                        article.id,
                        article.section,
                        article.title,
                        article._abstract,
                        article.publishedDate,
                        article.multimedia,
                        article.url
                )
        cachedArticles[cachedArticle.id] = cachedArticle
    }
}