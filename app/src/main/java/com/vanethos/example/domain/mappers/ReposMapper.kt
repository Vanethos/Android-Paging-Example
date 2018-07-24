package com.vanethos.example.domain.mappers

import com.vanethos.example.data.models.ReposDto
import com.vanethos.example.domain.models.Repos
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface ReposMapper {
    companion object {
        val Instance = Mappers.getMapper(ReposMapper::class.java)!!
    }

    fun map(repos : ReposDto?) : Repos
    fun mapList(repos : List<ReposDto>?) : List<Repos>
}