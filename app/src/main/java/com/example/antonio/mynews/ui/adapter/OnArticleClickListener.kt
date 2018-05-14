package com.example.antonio.mynews.ui.adapter

import com.example.antonio.mynews.data.Article

interface OnArticleClickListener {
    fun onArticleClicked(articleUrl: String)
    fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String)
    fun onArchiveArticleButtonClicked(article: Article)
}