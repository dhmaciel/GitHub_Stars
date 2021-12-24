package com.dhmaciel.githubstars.home.domain.mapper

import com.dhmaciel.githubstars.home.domain.model.GitList
import com.dhmaciel.githubstars.home.domain.model.GitListItem
import com.dhmaciel.githubstars.home.domain.model.GitListOwner
import com.dhmaciel.githubstars.home.domain.repository.model.GitListItemResponse
import com.dhmaciel.githubstars.home.domain.repository.model.GitListOwnerResponse
import com.dhmaciel.githubstars.home.domain.repository.model.GitListResponse

fun GitListResponse.toDomain() = GitList(
    totalCount = totalCount,
    incompleteResults = incompleteResults,
    items = items.map { it.toDomain() }
)

private fun GitListItemResponse.toDomain() = GitListItem(
    id = id,
    repoName = repoName,
    stargazersCount = stargazersCount,
    forksCount = forksCount,
    owner = owner.toDomain()
)

private fun GitListOwnerResponse.toDomain() = GitListOwner(
    id = id,
    login = login,
    avatarURL = avatarURL
)