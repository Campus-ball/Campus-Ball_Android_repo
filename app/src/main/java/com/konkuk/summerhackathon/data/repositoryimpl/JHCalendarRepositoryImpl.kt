package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.CalendarEventDto
import com.konkuk.summerhackathon.data.service.CalendarApiService
import com.konkuk.summerhackathon.domain.repository.CalendarRepository
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarApiService: CalendarApiService
) : CalendarRepository {
    override suspend fun getCalendarEvents(): Result<List<CalendarEventDto>> {
        return runCatching {
            val response = calendarApiService.getCalendarEvents()

            response.data.items
        }
    }
}