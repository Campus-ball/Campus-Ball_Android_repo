package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.UserMeResponse
import retrofit2.http.GET

interface UserApi {
    @GET("/user/me")
    suspend fun getMe(): UserMeResponse
}