package com.example.antonio.mynews.network

import com.example.antonio.mynews.data.Article
import com.google.gson.annotations.SerializedName

data class TopStoriesResponse(
        var section: String,
        @SerializedName("results")
        var articles: List<Article>
)