package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.NicknameCheckResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DuplCheckApi {
    @GET("api/nickname/check")
    suspend fun checkNickname(
        @Query("q") nickname: String
    ): NicknameCheckResponse

    @GET("api/userid/check")
    suspend fun checkUserId(
        @Query("q") userId: String
    ): NicknameCheckResponse


}