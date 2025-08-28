package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.CalendarEventDto

interface CalendarRepository {
    suspend fun getCalendarEvents(): Result<List<CalendarEventDto>>
}