package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.request.MatchRequest
import com.konkuk.summerhackathon.data.dto.request.MatchSendRequest
import com.konkuk.summerhackathon.data.dto.response.CalendarEventDto
import com.konkuk.summerhackathon.data.dto.response.MatchResponse
import com.konkuk.summerhackathon.data.dto.response.ReceivedMatchResponse

interface MatchRepository {
    suspend fun getMatchSuccessEvents(): Result<List<MatchResponse>>

    suspend fun getReceivedMatchSuccessEvents(): Result<List<ReceivedMatchResponse>>

    suspend fun acceptMatch(request: MatchRequest): Int

    suspend fun rejectMatch(request: MatchRequest): Int

    suspend fun sendMatch(request: MatchSendRequest): Int

}