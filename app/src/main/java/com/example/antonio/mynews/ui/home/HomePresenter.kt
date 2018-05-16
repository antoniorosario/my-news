package com.example.antonio.mynews.ui.home

class HomePresenter(private val homeView: HomeContract.View) : HomeContract.Presenter {

    init {
        homeView.presenter = this
    }

    override fun start() {
        homeView.setUpNavigationDrawer()
        homeView.setUpViewPagerAndTabs()
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


