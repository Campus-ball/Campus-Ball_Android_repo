package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApi {
    @Multipart
    @POST("/api/images/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part   // 서버가 요구하는 key가 "file"
    ): ImageUploadResponse
}