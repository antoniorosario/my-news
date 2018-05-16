package com.example.antonio.mynews.ui.topstories

import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.source.ArticlesDataSource
import com.example.antonio.mynews.data.source.ArticlesRepository

class TopStoriesPresenter(
        private val articlesRepository: ArticlesRepository,
        val topStoriesView: TopStoriesContract.View
) : TopStoriesContract.Presenter {

    init {
        topStoriesView.presenter = this
    }

    override fun start() {
        loadArticles(topStoriesView.section)
    }

    override fun loadArticles(section: String) {

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

    override fun fetchArticles(section: String) {
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

    override fun onArticleClicked(articleUrl: String) {
        topStoriesView.navigateToArticleUrl(articleUrl)
    }

    override fun onArchiveArticleButtonClicked(article: Article) {
        article.isArchived = !article.isArchived
        articlesRepository.updateArticle(article.id, article.isArchived)
        topStoriesView.showArticleArchiveConfirmation(article.isArchived)
    }

    override fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String) {
        topStoriesView.navigateToShareArticleChooser(articleTitle, articleUrl)
    }

}