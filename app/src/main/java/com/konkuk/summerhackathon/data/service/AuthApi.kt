package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.request.LoginRequest
import com.konkuk.summerhackathon.data.dto.request.RefreshRequest
import com.konkuk.summerhackathon.data.dto.response.LoginResponse
import com.konkuk.summerhackathon.data.dto.response.RefreshResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("/auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): RefreshResponse
}