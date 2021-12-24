package com.dhmaciel.githubstars.home.data.repository

import com.dhmaciel.githubstars.core.result.Result
import com.dhmaciel.githubstars.core.result.RetrofitResult
import com.dhmaciel.githubstars.home.data.remote.GitHubService
import com.dhmaciel.githubstars.home.domain.mapper.toDomain
import com.dhmaciel.githubstars.home.domain.model.GitList
import com.dhmaciel.githubstars.home.domain.repository.GitListRepository
import javax.inject.Inject

class GitListRepositoryImpl @Inject constructor(
    private val service: GitHubService, private val retrofitResult: RetrofitResult
) : GitListRepository {
    override suspend fun fetchGitHubList(pageNumber: Int): Result<GitList> {
        return retrofitResult.getResult { service.fetchRepositories(pageNumber) }.map { it.toDomain() }
    }
}