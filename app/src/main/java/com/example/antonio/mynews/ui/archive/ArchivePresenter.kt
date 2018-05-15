package com.example.antonio.mynews.ui.archive

import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.source.ArticlesDataSource
import com.example.antonio.mynews.data.source.ArticlesRepository

class ArchivePresenter(private val articlesRepository: ArticlesRepository, val archiveView: ArchiveContract.View) : ArchiveContract.Presenter {


    override fun start() {
        loadArchivedArticles()
    }

    override fun loadArchivedArticles() {
        articlesRepository.getArchivedArticles(object : ArticlesDataSource.LoadArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>) {
                archiveView.showArticles(articles)
            }

            override fun onDataNotAvailable() {
                archiveView.showEmptyText()
            }
        })
    }


    override fun onArticleClicked(articleUrl: String) {
        archiveView.navigateToArticleUrl(articleUrl)
    }

    override fun onArchiveArticleButtonClicked(article: Article) {
        article.isArchived = !article.isArchived
        articlesRepository.updateArticle(article)
        archiveView.showArticleArchiveConfirmation(article.isArchived)
    }

    override fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String) {
        archiveView.navigateToShareArticleChooser(articleTitle, articleUrl)
    }

}