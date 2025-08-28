package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: Int,
    val message: String,
    val data: TokenBundle
)

@Serializable
data class TokenBundle(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String // "bearer"
)