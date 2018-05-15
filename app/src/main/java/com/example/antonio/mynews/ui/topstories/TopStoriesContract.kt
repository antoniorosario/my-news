package com.example.antonio.mynews.ui.topstories

import android.support.design.widget.Snackbar
import com.example.antonio.mynews.ui.BaseArticlePresenter
import com.example.antonio.mynews.ui.BaseArticleView

interface TopStoriesContract {

    interface View : BaseArticleView<Presenter> {
        var section: String

        fun clearArticles()
        fun showFailedRequestMessage(): Snackbar
        fun showArticlesUpToDateMessage(): Snackbar
        fun hideRefreshingIndicator()
    }

    interface Presenter : BaseArticlePresenter {
        fun loadArticles(section: String)
        fun fetchArticles(section: String)
    }

}