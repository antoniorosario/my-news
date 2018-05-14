package com.example.antonio.mynews.ui

class HomePresenter {

    lateinit var homeView: HomeView

    fun onSendFeedbackClicked() {
        homeView.navigateToEmailClient()
    }

    fun onTabSelected(tabPosition: Int) {
        homeView.navigateToSelectedTab(tabPosition)
    }

    fun onArchivedDrawerItemClicked() {
        homeView.navigateToArchiveScreen()
    }
}