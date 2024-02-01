package com.example.thenews.di

import com.example.thenews.domain.NewsListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(): NewsListRepository {
        return NewsListRepository()
    }
}
