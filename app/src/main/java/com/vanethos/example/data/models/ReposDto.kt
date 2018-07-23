package com.vanethos.example.data.models

import com.google.gson.annotations.SerializedName

data class ReposDto(
        @SerializedName("name") var name : String?,
        @SerializedName("description") var description : String?,
        @SerializedName("language") var language : String?,
        @SerializedName("html_url") var url : String?,
        @SerializedName("stargazers_count") var startCount : Int?,
        @SerializedName("watchers_count") var watchersCount : Int?
) {
    // necessary for Mapstruct
    // check: https://github.com/mapstruct/mapstruct-examples/tree/master/mapstruct-kotlin
    constructor() : this(null, null, null, null, null, null)
}