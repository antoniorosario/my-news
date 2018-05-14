package com.example.antonio.mynews.ui.topstories

import android.support.design.widget.Snackbar
import com.example.antonio.mynews.data.Article

interface TopStoriesView {
    fun clearArticles()
    fun showFailedRequestMessage(): Snackbar
    fun showArticlesUpToDateMessage(): Snackbar
    fun hideRefreshingIndicator()

    //TODO Extract common View methods into a BaseView
    fun showArticles(articles: List<Article>)

    fun showArticleArchiveConfirmation(isArchived: Boolean)
    fun navigateToArticleUrl(articleUrl: String)
    fun navigateToShareArticleChooser(articleTitle: String, articleUrl: String)
}