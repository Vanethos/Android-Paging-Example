package com.vanethos.example.domain.managers

import android.accounts.NetworkErrorException
import com.vanethos.example.data.services.ReposService
import com.vanethos.example.domain.mappers.ReposMapper
import com.vanethos.example.domain.models.Repos
import io.reactivex.Single
import retrofit2.Response

class ReposManager {
    var reposService : ReposService = ReposService()

    fun getListOfRepos(user : String, page : Int, perPage : Int) : Single<List<Repos>> {
        return reposService.getReposForUser(user, page, perPage)
                .onErrorResumeNext({throwable -> Single.error(throwable)})
                .flatMap { response ->
                    if (!response.isSuccessful) {
                        Single.error(Throwable(response.code().toString()))
                    } else Single.just(response)
                }
                .map {response -> response.body()}
                .map { list -> ReposMapper.Instance.mapList(list) }
    }
}