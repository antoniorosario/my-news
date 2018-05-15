package com.example.antonio.mynews.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.antonio.mynews.data.source.local.ArticleTypeConverters
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(ArticleTypeConverters::class)
data class Article(
        @PrimaryKey var id: String,
        @ColumnInfo(name = "section")
        var section: String,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "_abstract")
        @SerializedName("abstract")
        val _abstract: String,
        @ColumnInfo(name = "publishedDate")
        @SerializedName("published_date")
        val publishedDate: String,
        @ColumnInfo(name = "multimedia")
        @SerializedName("multimedia")
        val multimedia: List<Multimedium>,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "isArchived")
        var isArchived: Boolean = false
)

data class Multimedium(val url: String)