package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.CalendarEventDto
import com.konkuk.summerhackathon.data.dto.response.MatchResponse
import com.konkuk.summerhackathon.data.dto.response.ReceivedMatchResponse

interface MatchRepository {
    suspend fun getMatchSuccessEvents(): Result<List<MatchResponse>>

    suspend fun getReceivedMatchSuccessEvents(): Result<List<ReceivedMatchResponse>>
}