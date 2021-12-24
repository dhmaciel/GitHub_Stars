package com.dhmaciel.githubstars.core.di

import com.dhmaciel.githubstars.core.result.RetrofitResult
import com.dhmaciel.githubstars.core.result.RetrofitResultImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GitListResultModule {

    @Binds
    fun provideRetrofitResult(retrofitResultImpl: RetrofitResultImpl): RetrofitResult
}