package com.konkuk.summerhackathon.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.konkuk.summerhackathon.BuildConfig
import com.konkuk.summerhackathon.data.service.AuthInterceptor
import com.konkuk.summerhackathon.data.service.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(
        authInterceptor: AuthInterceptor,
        // refresh API 준비되면 아래 주석 해제
        // tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)           // 네트워크 로그
            .addInterceptor(authInterceptor)   // AccessToken 자동 첨부
            // .authenticator(tokenAuthenticator) // 401 발생 시 refresh → 재요청
            .build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory =
        Json {
            ignoreUnknownKeys = true   // 응답에 정의 안된 필드 무시
            isLenient = true           // 유연한 파싱
            encodeDefaults = true
        }.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)   // build.gradle.kts에서 따옴표 포함으로 넣어야 함!
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
}