package com.example.antonio.mynews.ui

import com.example.antonio.mynews.data.Article

/*
 *  Base view for views that display Article objects data
 */
interface BaseArticleView<T> : BaseView<T> {

    fun showArticles(articles: List<Article>)
    fun showArticleArchiveConfirmation(isArchived: Boolean)
    fun navigateToArticleUrl(articleUrl: String)
    fun navigateToShareArticleChooser(articleTitle: String, articleUrl: String)
}