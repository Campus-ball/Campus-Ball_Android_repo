package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class RandomMatchRequestResponse(
    val clubId: Long,
    val startDate: String,
    val startTime: String
)
