package com.vanethos.example.domain.models

data class Repos(
        var name : String?,
        var description : String?,
        var language : String?,
        var url : String?,
        var startCount : Int?,
        var watchersCount : Int?
) {
    // necessary for Mapstruct
    // check: https://github.com/mapstruct/mapstruct-examples/tree/master/mapstruct-kotlin
    constructor() : this(null, null, null, null, null, null)
}