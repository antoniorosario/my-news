package com.example.antonio.mynews.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NYTimesApi {

    @GET("/svc/topstories/v2/{section}.json")
    fun getTopStoriesResponse(@Path("section") section: String): Call<TopStoriesResponse>

//    @GET("/svc/search/v2/articlesearch.json")
//    fun getSearchResponse(
//            @Query("query") query: String,
//            @Query("filteredQuery") filteredQuery: String,
//            @Query("sortOrder") sortOrder: String,
//            @Query("fields") fields: String): Call<SearchResponse>
}