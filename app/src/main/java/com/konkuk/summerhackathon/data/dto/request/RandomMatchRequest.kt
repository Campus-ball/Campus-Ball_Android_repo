package com.konkuk.summerhackathon.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RandomMatchRequest(
    val clubId: Long,
    val startDate: String,
    val startTime: String
)