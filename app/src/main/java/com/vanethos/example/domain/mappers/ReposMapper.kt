package com.vanethos.example.domain.mappers

import com.vanethos.example.data.models.ReposDto
import com.vanethos.example.domain.models.Repos
import org.mapstruct.Mapper

@Mapper
interface ReposMapper {
    fun map(repos : ReposDto) : Repos
    fun map(repos : List<ReposDto>) : List<ReposDto>
}