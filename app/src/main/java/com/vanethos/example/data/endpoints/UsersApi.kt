package com.vanethos.example.data.endpoints

import com.vanethos.example.data.models.ReposDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val PREFIX = "users/"

interface UsersApi {

    @GET(PREFIX + "{userId}/repos")
    fun getRepos(@Path("userId") userId: String,
                 @Query("page") page: Int,
                 @Query("per_page") perPage : Int
    ): Single<Response<List<ReposDto>>>
}