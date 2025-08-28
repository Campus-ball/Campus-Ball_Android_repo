package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.request.RandomMatchRequest
import com.konkuk.summerhackathon.data.dto.response.RandomMatchRequestResponse
import com.konkuk.summerhackathon.data.dto.response.RandomMatchResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchApi {
    @POST("/match/random")
    suspend fun getRandomMatch(): RandomMatchResponse

    @POST("/match/random/request")
    suspend fun requestRandomMatch(
        @Body body: RandomMatchRequest
    ): RandomMatchRequestResponse
}