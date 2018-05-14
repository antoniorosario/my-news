package com.example.antonio.mynews.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(apiKey.isEmpty()){
            TODO("Sign up for a NYTimes API key at: https://developer.nytimes.com/signup")
        }
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl
                .newBuilder()
                .addQueryParameter("api-key", apiKey)
                .build()

        // Request customization: add request headers
        val request = original
                .newBuilder()
                .url(url).build()

        return chain.proceed(request)
    }
}
