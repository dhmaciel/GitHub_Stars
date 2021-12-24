package com.dhmaciel.githubstars.home.domain.repository.model

import com.google.gson.annotations.SerializedName

data class GitListItemResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("stargazers_count")
    val stargazersCount: Long,

    @SerializedName("name")
    val repoName: String,

    @SerializedName("forks_count")
    val forksCount: Long,

    @SerializedName("owner")
    val owner: GitListOwnerResponse
)
