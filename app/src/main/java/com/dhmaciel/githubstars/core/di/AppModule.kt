package com.dhmaciel.githubstars.core.di

import android.content.Context
import com.dhmaciel.githubstars.GitHubApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): GitHubApplication {
        return app as GitHubApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: GitHubApplication): Context {
        return application.applicationContext
    }
}