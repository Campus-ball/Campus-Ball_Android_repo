package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageUploadResponse(
    val status: Int,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val imgUrl: String
    )
}
