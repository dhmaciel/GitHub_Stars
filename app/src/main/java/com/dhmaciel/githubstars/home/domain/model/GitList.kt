package com.dhmaciel.githubstars.home.domain.model

data class GitList(
    val totalCount: Long,

    val incompleteResults: Boolean,

    val items: List<GitListItem>
)
