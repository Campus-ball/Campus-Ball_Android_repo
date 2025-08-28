package com.konkuk.summerhackathon.di

import com.konkuk.summerhackathon.data.repositoryimpl.AuthRepositoryImpl
import com.konkuk.summerhackathon.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl
}