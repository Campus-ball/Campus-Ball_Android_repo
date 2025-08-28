package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.request.AvailabilityRequest
import com.konkuk.summerhackathon.data.dto.request.MatchRequest
import com.konkuk.summerhackathon.data.dto.request.MatchSendRequest
import com.konkuk.summerhackathon.data.dto.response.BaseMatchResponse
import com.konkuk.summerhackathon.data.dto.response.BaseReceivedMatchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MatchApiService {
    @GET("/match")
    suspend fun getMatchSuccessEvents(): BaseMatchResponse

    @GET("/match/list")
    suspend fun getReceivedMatchSuccessEvents(): BaseReceivedMatchResponse

    @POST("/match/accept")
    suspend fun acceptMatch(
        @Body request: MatchRequest
    ): Response<Unit>

    @POST("/match/reject")
    suspend fun rejectMatch(
        @Body request: MatchRequest
    ): Response<Unit>

    @POST("/match/request")
    suspend fun sendMatch(
        @Body request: MatchSendRequest
    ): Response<Unit>
}