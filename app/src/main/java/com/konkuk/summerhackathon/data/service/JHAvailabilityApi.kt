package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.request.AvailabilityRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AvailabilityApi {
    @POST("/availability")
    suspend fun registerAvailability(
        @Body request: AvailabilityRequest
    ): Response<Unit>
}