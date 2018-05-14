package com.example.antonio.mynews.ui.topstories

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TopStoriesPagerAdapter(fragmentManager: FragmentManager, private val sections: Array<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? =
            TopStoriesFragment.newInstance(sections[position])

    override fun getPageTitle(position: Int): CharSequence? = sections[position]

    override fun getCount(): Int = sections.size
}