package com.dhmaciel.githubstars.home.domain.model

data class GitListItem(
    val id: Long,

    val repoName: String,

    val stargazersCount: Long,

    val forksCount: Long,

    val owner: GitListOwner
)
