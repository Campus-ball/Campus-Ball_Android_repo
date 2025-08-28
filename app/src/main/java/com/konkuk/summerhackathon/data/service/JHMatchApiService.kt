package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.BaseMatchResponse
import com.konkuk.summerhackathon.data.dto.response.BaseReceivedMatchResponse
import retrofit2.http.GET

interface MatchApiService {
    @GET("/match/")
    suspend fun getMatchSuccessEvents(): BaseMatchResponse

    @GET("/match/list/")
    suspend fun getReceivedMatchSuccessEvents(): BaseReceivedMatchResponse
}