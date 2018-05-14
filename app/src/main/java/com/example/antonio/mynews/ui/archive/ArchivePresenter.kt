package com.example.antonio.mynews.ui.archive

import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.source.ArticlesDataSource
import com.example.antonio.mynews.data.source.ArticlesRepository

//TODO Extract common Presenter methods into a BasePresenter
class ArchivePresenter(private val articlesRepository: ArticlesRepository) {

    lateinit var archiveView: ArchiveView

    fun loadArchivedArticles() {
        articlesRepository.getArchivedArticles(object : ArticlesDataSource.LoadArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>) {
                archiveView.showArticles(articles)
            }

            override fun onDataNotAvailable() {
                archiveView.showEmptyText()
            }
        })
    }

    fun onArticleClicked(articleUrl: String) {
        archiveView.navigateToArticleUrl(articleUrl)
    }

    fun onArchiveArticleButtonClicked(article: Article) {
        article.isArchived = !article.isArchived
        articlesRepository.updateArticle(article)
        archiveView.showArticleArchiveConfirmation(article.isArchived)
    }

    fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String) {
        archiveView.navigateToShareArticleChooser(articleTitle, articleUrl)
    }
}