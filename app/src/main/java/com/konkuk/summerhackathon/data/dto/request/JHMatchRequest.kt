package com.konkuk.summerhackathon.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchRequest(
    @SerialName("requestId")
    val requestId: Int
)
