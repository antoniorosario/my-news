package com.example.antonio.mynews.ui.home

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomePresenterTest {
    @Mock private lateinit var mockHomeView: HomeContract.View

    private lateinit var homePresenter: HomePresenter

    @Before fun setUpHomePresenter() {
        MockitoAnnotations.initMocks(this)

        homePresenter = HomePresenter(mockHomeView)
    }


    @Test fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        homePresenter = HomePresenter(mockHomeView)

        // Then the presenter is set to the view
        Mockito.verify(mockHomeView).presenter = homePresenter
    }

    @Test fun start_shouldSetUpViewPagerTabsAndNavigationDrawer() {
        homePresenter.start()

        Mockito.verify(mockHomeView).setUpViewPagerAndTabs()
        Mockito.verify(mockHomeView).setUpNavigationDrawer()
    }

    @Test fun onSendFeedbackClicked_shouldNavigateToEmailClient() {
        homePresenter.onSendFeedbackClicked()

        Mockito.verify(mockHomeView).navigateToEmailClient()
    }

    @Test fun onTabSelected_shouldNavigateToSelectedTab() {
        val tabPosition = 1
        homePresenter.onTabSelected(tabPosition)

        Mockito.verify(mockHomeView).navigateToSelectedTab(tabPosition)
    }

    @Test fun onArchiveDrawerItemClicked_shouldNavigateToArchiveScreen() {
        homePresenter.onArchivedDrawerItemClicked()

        Mockito.verify(mockHomeView).navigateToArchiveScreen()
    }
}