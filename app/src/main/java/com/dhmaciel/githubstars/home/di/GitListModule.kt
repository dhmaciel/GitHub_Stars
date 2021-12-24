package com.dhmaciel.githubstars.home.di

import com.dhmaciel.githubstars.home.data.repository.GitListRepositoryImpl
import com.dhmaciel.githubstars.home.domain.repository.GitListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GitListModule {

    @Binds
    fun provideGitListRepository(repository: GitListRepositoryImpl): GitListRepository

}