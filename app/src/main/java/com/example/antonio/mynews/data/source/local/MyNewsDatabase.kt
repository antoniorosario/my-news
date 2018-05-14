package com.example.antonio.mynews.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.antonio.mynews.data.Article

@Database(entities = [(Article::class)], version = 1)
abstract class MyNewsDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao

    companion object {
        private var INSTANCE: MyNewsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): MyNewsDatabase? {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MyNewsDatabase::class.java, "MyNews.db")
                            .build()
                }
                return INSTANCE!!
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}