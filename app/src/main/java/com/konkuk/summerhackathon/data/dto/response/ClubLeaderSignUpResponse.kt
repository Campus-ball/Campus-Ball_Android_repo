package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ClubLeaderSignUpResponse(
    val status: Int,
    val message: String,
    val data: String? = null
)
