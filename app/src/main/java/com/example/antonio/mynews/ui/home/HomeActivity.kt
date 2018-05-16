package com.example.antonio.mynews.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.antonio.mynews.R
import com.example.antonio.mynews.di.Injector
import com.example.antonio.mynews.ui.archive.ArchiveActivity
import com.example.antonio.mynews.ui.topstories.TopStoriesPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity(), HomeContract.View {
    companion object {
        const val OFF_SCREEN_PAGE_LIMIT = 2
    }

    override lateinit var presenter: HomeContract.Presenter
    private lateinit var pagerAdapter: TopStoriesPagerAdapter
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = Injector.provideHomePresenter(this)
        presenter.start()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun setUpUI() {
        setUpViewPagerAndTabs()
        setSupportActionBar(toolbar)
        setUpNavigationDrawer()
    }

    private fun setUpViewPagerAndTabs() {
        pagerAdapter = Injector.provideTopStoriesPagerAdapter(supportFragmentManager, resources)
        view_pager.adapter = pagerAdapter
        view_pager.offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
        tab_layout.setupWithViewPager(view_pager)
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                presenter.onTabSelected(tab.position)
            }

            // Methods left blank intentionally
            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setUpNavigationDrawer() {
        drawerToggle = Injector.provideActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )

        drawer_layout.addDrawerListener(drawerToggle)

        setupDrawerContent(navigation_view)
        navigation_view.setCheckedItem(R.id.nav_health_fragment)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_health_fragment -> view_pager.currentItem = 0
            R.id.nav_science_fragment -> view_pager.currentItem = 1
            R.id.nav_technology_fragment -> view_pager.currentItem = 2
            R.id.nav_sports_fragment -> view_pager.currentItem = 3
            R.id.nav_world_fragment -> view_pager.currentItem = 4
            R.id.nav_archived_article_activity -> {
                presenter.onArchivedDrawerItemClicked()
            }
//            R.id.nav_article_search_fragment -> {
//                val searchIntent = ArticleSearchActivity.newIntent(this)
//                startActivity(searchIntent)
//            }
            R.id.action_send_feedback -> presenter.onSendFeedbackClicked()
            else -> view_pager.currentItem = 0
        }
        if (menuItem.isCheckable) {
            menuItem.isChecked = true
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun navigateToEmailClient() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: antoniorosario3@gmail.com") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_feedback_subject))
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.send_feedback_text))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun navigateToSelectedTab(tabPosition: Int) {
        navigation_view.menu.getItem(tabPosition).isChecked = true
    }

    override fun navigateToArchiveScreen() {
        startActivity(ArchiveActivity.newIntent(this))
    }
}