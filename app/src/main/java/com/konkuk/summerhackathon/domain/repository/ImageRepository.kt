package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.ImageUploadResponse
import okhttp3.MultipartBody

interface ImageRepository {
    suspend fun uploadImage(filePart: MultipartBody.Part): Result<ImageUploadResponse>
}