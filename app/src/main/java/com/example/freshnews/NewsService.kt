package com.example.freshnews

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "12696f38705c400e907f03a536bb5e44"

interface NewsService {

    //URL will be -: https://newsapi.org/v2/top-headlines/?apiKey=12696f38705c400e907f03a536bb5e44&country=in&page=1
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getNews(@Query("country") country: String, @Query("page") page: Int): Call<Article>
}

object RetrofitClientInstance {

    val newsInstance: NewsService

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        newsInstance = retrofit.create(NewsService::class.java)
    }
}