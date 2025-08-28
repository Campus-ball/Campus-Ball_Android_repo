package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.ImageUploadResponse
import com.konkuk.summerhackathon.data.service.ImageApi
import com.konkuk.summerhackathon.domain.repository.ImageRepository
import javax.inject.Inject
import okhttp3.MultipartBody

class ImageRepositoryImpl @Inject constructor(
    private val api: ImageApi
) : ImageRepository {

    override suspend fun uploadImage(filePart: MultipartBody.Part): Result<ImageUploadResponse> =
        runCatching { api.uploadImage(filePart) }
}