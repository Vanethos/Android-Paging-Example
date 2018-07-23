package com.vanethos.example.domain.managers

import com.vanethos.example.data.services.ReposService
import com.vanethos.example.domain.mappers.ReposMapper
import com.vanethos.example.domain.models.Repos
import io.reactivex.Single

class ReposManager {
    var reposService : ReposService
    init {
        reposService = ReposService()
    }

    fun getListOfRepos(user : String, page : Int, perPage : Int) : Single<List<Repos>> {
        return reposService.getReposForUser(user, page, perPage)
                .map( { list -> ReposMapper.Instance.mapList(list.body()) } )
    }
}