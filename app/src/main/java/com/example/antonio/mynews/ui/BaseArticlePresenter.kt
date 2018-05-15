package com.example.antonio.mynews.ui

import com.example.antonio.mynews.data.Article

/*
 * Base presenter for presenters that process Article object data
 */
interface BaseArticlePresenter : BasePresenter {

    fun onArticleClicked(articleUrl: String)

    fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String)

    fun onArchiveArticleButtonClicked(article: Article)
}