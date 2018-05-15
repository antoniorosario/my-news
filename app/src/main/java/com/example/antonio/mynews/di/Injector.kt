package com.example.antonio.mynews.di

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.example.antonio.mynews.API_KEY
import com.example.antonio.mynews.BASE_URL
import com.example.antonio.mynews.R
import com.example.antonio.mynews.data.source.ArticlesRepository
import com.example.antonio.mynews.data.source.local.ArticlesDao
import com.example.antonio.mynews.data.source.local.ArticlesLocalDataSource
import com.example.antonio.mynews.data.source.local.MyNewsDatabase
import com.example.antonio.mynews.data.source.remote.ArticlesRemoteDataSource
import com.example.antonio.mynews.network.ApiKeyInterceptor
import com.example.antonio.mynews.network.NYTimesApi
import com.example.antonio.mynews.ui.BaseArticlePresenter
import com.example.antonio.mynews.ui.adapter.ArticleAdapter
import com.example.antonio.mynews.ui.archive.ArchiveContract
import com.example.antonio.mynews.ui.archive.ArchivePresenter
import com.example.antonio.mynews.ui.home.HomeContract
import com.example.antonio.mynews.ui.home.HomePresenter
import com.example.antonio.mynews.ui.topstories.TopStoriesContract
import com.example.antonio.mynews.ui.topstories.TopStoriesPagerAdapter
import com.example.antonio.mynews.ui.topstories.TopStoriesPresenter
import com.example.antonio.mynews.utils.AppExecutors
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// TODO use Dagger 2 for DI.
object Injector {

    fun provideTopStoriesPresenter(uiController: FragmentActivity, topStoriesView: TopStoriesContract.View) =
            TopStoriesPresenter(provideArticlesRepository(uiController), topStoriesView)

    private fun provideArticlesRepository(context: Context): ArticlesRepository {
        val myNewsDatabase = provideMyNewsDatabase(context)

        return ArticlesRepository(
                provideArticlesRemoteDataSource(),
                provideArticlesLocalDataSource(myNewsDatabase!!.articlesDao())
        )
    }

    private fun provideMyNewsDatabase(context: Context) = MyNewsDatabase.getInstance(context)

    private fun provideArticlesLocalDataSource(articlesDao: ArticlesDao) =
            ArticlesLocalDataSource(provideAppExecutors(), articlesDao)

    private fun provideAppExecutors() = AppExecutors()

    private fun provideArticlesRemoteDataSource() = ArticlesRemoteDataSource(provideNyTimesApi())

    fun provideArticleAdapter() = ArticleAdapter()

    fun provideLayoutManager(fragmentActivity: FragmentActivity?) = LinearLayoutManager(fragmentActivity)

    fun provideTopStoriesPagerAdapter(supportFragmentManager: FragmentManager, resources: Resources) =
            TopStoriesPagerAdapter(supportFragmentManager, resources.getStringArray(R.array.sections))

    fun provideActionBarDrawerToggle(
            activity: Activity,
            drawerLayout: DrawerLayout,
            toolbar: Toolbar,
            openDrawerResId: Int,
            closeDrawerResId: Int
    ) = ActionBarDrawerToggle(activity, drawerLayout, toolbar, openDrawerResId, closeDrawerResId)

    private fun provideNyTimesApi() = provideRetrofit(BASE_URL).create(NYTimesApi::class.java)

    private fun provideRetrofit(baseUrl: String): Retrofit =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient()).build()

    private fun provideOkHttpClient() =
            OkHttpClient.Builder()
                    .addInterceptor(provideAccessTokenInterceptor(API_KEY))
                    .build()

    private fun provideAccessTokenInterceptor(apiKey: String) = ApiKeyInterceptor(apiKey)

    fun provideHomePresenter(homeView: HomeContract.View): HomePresenter = HomePresenter(homeView)

    fun provideArchivePresenter(uiController: FragmentActivity, archiveView: ArchiveContract.View) =
            ArchivePresenter(provideArticlesRepository(uiController), archiveView)


}
