package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameCheckResponse(
    @SerialName("status")
    val status: Int,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: NicknameValidationData
)

@Serializable
data class NicknameValidationData(
    @SerialName("isValid")
    val isValid: Boolean
)