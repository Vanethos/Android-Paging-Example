package com.vanethos.example.data.services

import com.vanethos.example.data.endpoints.UsersApi
import com.vanethos.example.data.models.ReposDto
import com.vanethos.example.utils.networking.NetworkTools
import io.reactivex.Single
import retrofit2.Response

class ReposService {
    var api : UsersApi = NetworkTools.retrofit.create(UsersApi::class.java)

    fun getReposForUser(user : String, page : Int, perPage : Int) : Single<Response<List<ReposDto>>> {
        return api.getRepos(user, page, perPage)
    }
}