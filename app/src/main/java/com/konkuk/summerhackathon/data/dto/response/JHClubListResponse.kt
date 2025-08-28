package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClubListResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: ClubList
) {
    @Serializable
    data class ClubList(
        @SerialName("items")
        val items: List<Club>
    )

    @Serializable
    data class Club(
        @SerialName("clubId")
        val clubId: Int,
        @SerialName("clubName")
        val clubName: String
    )
}
