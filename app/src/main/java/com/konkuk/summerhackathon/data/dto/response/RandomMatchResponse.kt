package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class RandomMatchResponse(
    val status: Int,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val clubId: Long,
        val clubName: String,
        val departmentName: String,
        val clubLogoUrl: String,
        val clubDescription: String,
        val startDate: String,   // "YYYY-MM-DD"
        val startTime: String    // "HH:mm"
    )
}
