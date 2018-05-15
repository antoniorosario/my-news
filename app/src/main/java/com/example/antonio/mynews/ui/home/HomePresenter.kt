package com.example.antonio.mynews.ui.home

class HomePresenter(val homeView: HomeContract.View) : HomeContract.Presenter {

    init {
        homeView.presenter = this
    }

    override fun start() {
        homeView.setUpUI()
    }

    override fun onSendFeedbackClicked() {
        homeView.navigateToEmailClient()
    }

    override fun onTabSelected(tabPosition: Int) {
        homeView.navigateToSelectedTab(tabPosition)
    }

    override fun onArchivedDrawerItemClicked() {
        homeView.navigateToArchiveScreen()
    }
}


