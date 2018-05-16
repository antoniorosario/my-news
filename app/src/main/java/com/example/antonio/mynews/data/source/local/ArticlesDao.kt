package com.example.antonio.mynews.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.antonio.mynews.data.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>)

    //TODO Update article isArchived value instead of inserting a new article object into the DB.
    @Query("UPDATE Article SET isArchived = :isArchived WHERE id = :id")
    fun updateArticle(id: String, isArchived: Int)

    @Query("SELECT * FROM Article WHERE section = :section")
    fun loadArticlesBySection(section: String): List<Article>

    @Query("SELECT * FROM Article WHERE isArchived = 1")
    fun loadArchivedArticles(): List<Article>

    @Query("SELECT * FROM Article WHERE id = :id")
    fun loadArticleByID(id:String): Article?

    @Query("DELETE FROM Article WHERE id = :id")
    fun deleteArticleById(id: String): Int
}
