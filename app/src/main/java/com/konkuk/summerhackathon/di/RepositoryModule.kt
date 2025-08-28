package com.konkuk.summerhackathon.di

import com.konkuk.summerhackathon.data.repositoryimpl.AuthRepositoryImpl
import com.konkuk.summerhackathon.data.repositoryimpl.AvailabilityRepositoryImpl
import com.konkuk.summerhackathon.domain.repository.AuthRepository
import com.konkuk.summerhackathon.data.repositoryimpl.CalendarRepositoryImpl
import com.konkuk.summerhackathon.data.repositoryimpl.CollegeRepositoryImpl
import com.konkuk.summerhackathon.data.repositoryimpl.DepartmentRepositoryImpl
import com.konkuk.summerhackathon.domain.repository.CalendarRepository
import com.konkuk.summerhackathon.domain.repository.CollegeRepository
import com.konkuk.summerhackathon.domain.repository.DepartmentRepository
import com.konkuk.summerhackathon.data.repositoryimpl.UserRepositoryImpl
import com.konkuk.summerhackathon.domain.repository.CalendarRepository
import com.konkuk.summerhackathon.domain.repository.UserRepository
import dagger.Binds
import com.konkuk.summerhackathon.data.repositoryimpl.MatchRepositoryImpl
import com.konkuk.summerhackathon.domain.repository.AvailabilityRepository
import com.konkuk.summerhackathon.domain.repository.CalendarRepository
import com.konkuk.summerhackathon.domain.repository.MatchRepository
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
    fun provideMatchRepository(impl: MatchRepositoryImpl): MatchRepository = impl

    @Provides
    @Singleton
    fun provideAvailabilityRepository(impl: AvailabilityRepositoryImpl): AvailabilityRepository =
        impl

    @Provides
    @Singleton
    fun provideScheduleRepository(impl: CalendarRepositoryImpl): CalendarRepository = impl

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl


    @Provides
    @Singleton
    fun provideCollegeRepository(impl: CollegeRepositoryImpl): CollegeRepository = impl

    @Provides @Singleton
    fun provideDepartmentRepository(impl: DepartmentRepositoryImpl): DepartmentRepository = impl

    @Provides @Singleton
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl
}