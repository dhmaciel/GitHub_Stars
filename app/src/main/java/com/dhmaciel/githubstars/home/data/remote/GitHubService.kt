package com.dhmaciel.githubstars.home.data.remote

import com.dhmaciel.githubstars.home.domain.repository.model.GitListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories?q=language:kotlin&sort=stars")
    suspend fun fetchRepositories(@Query("page") pageNumber: Int): Response<GitListResponse>
}