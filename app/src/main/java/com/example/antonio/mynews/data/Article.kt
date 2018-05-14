package com.example.antonio.mynews.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
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
        //TODO Research type converters
//        @ColumnInfo(name = "multimedia")
//        @SerializedName("multimedia")
//        @TypeConverters(MultimediaTypeConverters::class)
//        val multimedia: List<Multimedium>,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "isArchived")
        var isArchived: Boolean = false
)

data class Multimedium(val url: String)