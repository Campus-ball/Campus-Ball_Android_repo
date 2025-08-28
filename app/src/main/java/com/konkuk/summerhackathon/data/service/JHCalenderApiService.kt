package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.CalendarResponse
import retrofit2.http.GET

interface CalendarApiService {
    @GET("/event/list")
    suspend fun getCalendarEvents(): CalendarResponse
}