package com.konkuk.summerhackathon.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailabilityRequest(
    @SerialName("startDate")
    val startDate: String,

    @SerialName("startTime")
    val startTime: String,

    @SerialName("endTime")
    val endTime: String,

    @SerialName("isRecurring")
    val isRecurring: Boolean
)
