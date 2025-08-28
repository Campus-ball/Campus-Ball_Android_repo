package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    val status: Int,
    val message: String,
    val data: TokenBundle
)
