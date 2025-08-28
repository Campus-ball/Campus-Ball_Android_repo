package com.konkuk.summerhackathon.di

import com.konkuk.summerhackathon.data.repositoryimpl.AuthRepositoryImpl
import com.konkuk.summerhackathon.domain.repository.AuthRepository
import com.konkuk.summerhackathon.data.repositoryimpl.CalendarRepositoryImpl
import com.konkuk.summerhackathon.data.repositoryimpl.UserRepositoryImpl
import com.konkuk.summerhackathon.domain.repository.CalendarRepository
import com.konkuk.summerhackathon.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
//    @Binds
//    @Singleton
//    abstract fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Provides
    @Singleton
    fun provideScheduleRepository(impl: CalendarRepositoryImpl): CalendarRepository = impl

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides @Singleton
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl
}