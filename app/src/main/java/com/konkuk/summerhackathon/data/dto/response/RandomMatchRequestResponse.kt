package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomMatchRequestResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: Data
) {
    @Serializable
    data class Data(
        @SerialName("requestId") val requestId: Long
    )
}

