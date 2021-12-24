package com.dhmaciel.githubstars.home.domain.repository.model

import com.google.gson.annotations.SerializedName

data class GitListOwnerResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarURL: String
)
