package com.dhmaciel.githubstars.core.di

import android.content.Context
import com.dhmaciel.githubstars.GitHubApplication
import com.dhmaciel.githubstars.core.interceptor.CacheInterceptor
import com.dhmaciel.githubstars.core.utils.Constants.BASE_URL
import com.dhmaciel.githubstars.core.utils.Constants.CACHE_SIZE_BYTES
import com.dhmaciel.githubstars.home.data.remote.GitHubService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        return Cache(context.cacheDir, CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    internal fun provideCacheInterceptor() = CacheInterceptor()

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        cacheInterceptor: CacheInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .cache(cache)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideGitHubApiService(retrofit: Retrofit): GitHubService =
        retrofit.create(GitHubService::class.java)

    @Provides
    fun provideGson() = Gson()
}