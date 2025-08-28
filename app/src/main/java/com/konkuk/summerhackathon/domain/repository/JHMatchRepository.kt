package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.CalendarEventDto
import com.konkuk.summerhackathon.data.dto.response.MatchResponse

interface MatchRepository {
    suspend fun getMatchSuccessEvents(): Result<List<MatchResponse>>
}