package com.example.antonio.mynews.ui.home

import com.example.antonio.mynews.ui.BasePresenter
import com.example.antonio.mynews.ui.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun navigateToEmailClient()
        fun navigateToSelectedTab(tabPosition: Int)
        fun navigateToArchiveScreen()
        fun setUpUI()
    }

    interface Presenter : BasePresenter {
        fun onSendFeedbackClicked()

        fun onTabSelected(tabPosition: Int)

        fun onArchivedDrawerItemClicked()
    }
}