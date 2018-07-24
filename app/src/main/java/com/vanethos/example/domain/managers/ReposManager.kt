package com.vanethos.example.domain.managers

import android.accounts.NetworkErrorException
import com.vanethos.example.data.services.ReposService
import com.vanethos.example.domain.mappers.ReposMapper
import com.vanethos.example.domain.models.Repos
import io.reactivex.Single
import retrofit2.Response

/**
 * Class that connects the Data layer to Presentation, where the API objects are manipulated and observed by
 * the Views (Activity, Fragment or View)
 */
class ReposManager {
    var reposService : ReposService = ReposService()

    fun getListOfRepos(user : String, page : Int, perPage : Int) : Single<List<Repos>> {
        return reposService.getReposForUser(user, page, perPage)
                // By calling `onErrorResumeNext` we could apply our own error handling function
                .onErrorResumeNext({throwable -> Single.error(throwable)})
                // Since we are using Retrofit's Response, we will need to parse it and check
                // if the response was successful or not
                .flatMap { response ->
                    if (!response.isSuccessful) {
                        Single.error(Throwable(response.code().toString()))
                    } else Single.just(response)
                }
                // If the response is successful, we retrieve its body
                .map {response -> response.body()}
                // Map the items to domain so that the presentation layer can handle it
                .map { list -> ReposMapper.Instance.mapList(list) }
    }
}