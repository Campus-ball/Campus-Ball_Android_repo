package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class CollegeListResponse(
    val status: Int,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val items: List<College>
    )

    @Serializable
    data class College(
        val collegeId: Int,
        val collegeName: String
    )
}
