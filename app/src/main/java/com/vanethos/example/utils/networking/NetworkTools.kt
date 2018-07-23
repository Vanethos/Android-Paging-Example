package com.vanethos.example.utils.networking

import com.google.gson.Gson
import com.vanethos.example.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkTools {
    init {
        var retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var gson = Gson()
    }
}