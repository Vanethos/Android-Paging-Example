package com.vanethos.example.utils.networking

import com.google.gson.Gson
import com.vanethos.example.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkTools {
    val retrofit : Retrofit
    val gson = Gson()
    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val gson = Gson()
    }
}