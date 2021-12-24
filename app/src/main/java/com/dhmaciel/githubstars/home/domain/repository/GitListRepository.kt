package com.dhmaciel.githubstars.home.domain.repository

import com.dhmaciel.githubstars.core.result.Result
import com.dhmaciel.githubstars.home.domain.model.GitList

interface GitListRepository {

    suspend fun fetchGitHubList(pageNumber: Int): Result<GitList>
}