package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserMeResponse(
    val status: Int,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val name: String,
        val nickname: String,
        val gender: String,
        val clubName: String,
        val phoneNumber: String,
        val chatUrl: String
    )
}
