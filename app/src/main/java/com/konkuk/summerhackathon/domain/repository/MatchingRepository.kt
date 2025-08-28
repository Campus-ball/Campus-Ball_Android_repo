package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.request.RandomMatchRequest
import com.konkuk.summerhackathon.data.dto.response.RandomMatchRequestResponse
import com.konkuk.summerhackathon.data.dto.response.RandomMatchResponse

interface MatchingRepository {
    suspend fun getRandomMatch(): Result<RandomMatchResponse>
    suspend fun requestRandomMatch(body: RandomMatchRequest): Result<RandomMatchRequestResponse>
}