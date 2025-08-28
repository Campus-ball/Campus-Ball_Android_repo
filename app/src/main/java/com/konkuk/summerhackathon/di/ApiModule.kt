package com.konkuk.summerhackathon.di

import com.konkuk.summerhackathon.data.service.CalendarApiService
import com.konkuk.summerhackathon.data.service.AuthApi
import com.konkuk.summerhackathon.data.service.CollegeApi
import com.konkuk.summerhackathon.data.service.DepartmentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
//    @Provides
//    @Singleton
//    fun providesHomeService(retrofit: Retrofit): HomeService =
//        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun providesCalenderService(retrofit: Retrofit): CalendarApiService =
        retrofit.create(CalendarApiService::class.java)

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideCollegeApi(retrofit: Retrofit): CollegeApi =
        retrofit.create(CollegeApi::class.java)

    @Provides @Singleton
    fun provideDepartmentApi(retrofit: Retrofit): DepartmentApi =
        retrofit.create(DepartmentApi::class.java)
}