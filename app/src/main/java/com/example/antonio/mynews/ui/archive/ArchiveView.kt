package com.example.antonio.mynews.ui.archive

import com.example.antonio.mynews.data.Article

interface ArchiveView {

    fun showEmptyText()

    //TODO Extract common View methods into a BaseView
    fun showArticles(articles: List<Article>)

    fun showArticleArchiveConfirmation(isArchived: Boolean)
    fun navigateToArticleUrl(articleUrl: String)
    fun navigateToShareArticleChooser(articleTitle: String, articleUrl: String)

}