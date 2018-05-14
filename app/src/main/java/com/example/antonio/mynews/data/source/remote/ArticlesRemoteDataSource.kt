package com.example.antonio.mynews.data.source.remote

import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.source.ArticlesDataSource
import com.example.antonio.mynews.network.NYTimesApi
import com.example.antonio.mynews.network.TopStoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ArticlesRemoteDataSource(private val nyTimesApi: NYTimesApi) : ArticlesDataSource {

    override fun getArticles(section: String, callback: ArticlesDataSource.LoadArticlesCallback) {
        val call = nyTimesApi.getTopStoriesResponse(section)
        call.enqueue(object : Callback<TopStoriesResponse> {
            override fun onResponse(call: Call<TopStoriesResponse>, response: Response<TopStoriesResponse>) {
                if (response.isSuccessful) {
                    lateinit var articles: List<Article>

                    val topStoriesResponse = response.body()

                    if (topStoriesResponse != null) {
                        articles = topStoriesResponse.articles
                        articles.forEach {
                            // initialize articles with id and section
                            it.id = UUID.randomUUID().toString()
                            it.section = topStoriesResponse.section
                        }
                    }

                    callback.onArticlesLoaded(articles)
                } else {
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<TopStoriesResponse>, t: Throwable) {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun saveArticles(articles: List<Article>) {
        // We don't control the api so don't do anything.

    }

    override fun updateArticle(article: Article) {
        // We don't control the api so don't do anything.
    }

    override fun deleteArticles(id: String) {
        // We don't control the api so don't do anything.
    }

    override fun getArchivedArticles(callback: ArticlesDataSource.LoadArticlesCallback) {
        // We don't control the api so don't do anything.
    }
}