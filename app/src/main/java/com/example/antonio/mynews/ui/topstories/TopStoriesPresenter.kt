package com.example.antonio.mynews.ui.topstories

import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.source.ArticlesDataSource
import com.example.antonio.mynews.data.source.ArticlesRepository

//TODO Extract common Presenter methods into a BasePresenter
class TopStoriesPresenter(private val articlesRepository: ArticlesRepository) {

    lateinit var topStoriesView: TopStoriesView

    fun loadArticles(section: String) {

        articlesRepository.getArticles(section, object : ArticlesDataSource.LoadArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>) {
                topStoriesView.clearArticles()
                topStoriesView.showArticles(articles)
            }

            override fun onDataNotAvailable() {
                topStoriesView.showFailedRequestMessage()
            }
        })
    }

    fun fetchArticles(section: String) {
        articlesRepository.getArticlesFromRemoteDataSource(section, object : ArticlesDataSource.LoadArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>) {
                topStoriesView.hideRefreshingIndicator()
                topStoriesView.clearArticles()
                topStoriesView.showArticles(articles)
                topStoriesView.showArticlesUpToDateMessage()
            }

            override fun onDataNotAvailable() {
                topStoriesView.hideRefreshingIndicator()
                topStoriesView.showFailedRequestMessage()
            }
        })
    }

    fun onArticleClicked(articleUrl: String) {
        topStoriesView.navigateToArticleUrl(articleUrl)
    }

    fun onArchiveArticleButtonClicked(article: Article) {
        article.isArchived = !article.isArchived
        articlesRepository.updateArticle(article)
        topStoriesView.showArticleArchiveConfirmation(article.isArchived)
    }

    fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String) {
        topStoriesView.navigateToShareArticleChooser(articleTitle, articleUrl)
    }
}