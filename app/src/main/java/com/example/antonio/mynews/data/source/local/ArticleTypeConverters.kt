package com.example.antonio.mynews.data.source.local

import android.arch.persistence.room.TypeConverter
import com.example.antonio.mynews.data.Multimedium
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ArticleTypeConverters {

    companion object {
        private var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToMultimediaList(data: String?): List<Multimedium> {
            if (data == null) {
                return Collections.emptyList()
            }

            val listType = object : TypeToken<List<Multimedium>>() {

            }.type

            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun multimediaListToString(multimedia: List<Multimedium>): String {
            return gson.toJson(multimedia)
        }
    }
}