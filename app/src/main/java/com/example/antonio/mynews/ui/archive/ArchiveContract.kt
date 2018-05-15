package com.example.antonio.mynews.ui.archive

import com.example.antonio.mynews.ui.BaseArticlePresenter
import com.example.antonio.mynews.ui.BaseArticleView

interface ArchiveContract {

    interface View : BaseArticleView<Presenter> {
        fun showEmptyText()
    }

    interface Presenter : BaseArticlePresenter {
        fun loadArchivedArticles()
    }
}