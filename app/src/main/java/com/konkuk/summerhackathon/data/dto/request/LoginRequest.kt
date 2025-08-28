package com.konkuk.summerhackathon.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val userId: String,
    val password: String
)
