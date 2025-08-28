package com.konkuk.summerhackathon.data.dto.response

import com.konkuk.summerhackathon.presentation.schedule.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 캘린더 이벤트 dto
@Serializable
data class CalendarEventDto(
    @SerialName("endDate")
    val endDate: String,
    @SerialName("endTime")
    val endTime: String,
    @SerialName("eventId")
    val eventId: Int,
    @SerialName("eventType")
    val eventType: EventType,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("startTime")
    val startTime: String,
    @SerialName("title")
    val title: String
)

@Serializable
data class CalendarListResponse(
    @SerialName("items")
    val items: List<CalendarEventDto>
)

@Serializable
data class CalendarResponse(
    @SerialName("status")
    val status: Int,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: CalendarListResponse
)