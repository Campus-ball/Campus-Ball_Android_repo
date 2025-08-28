package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.request.AvailabilityRequest
import com.konkuk.summerhackathon.data.service.AvailabilityApi
import retrofit2.Response

/*class AvailabilityRepository(
    private val api: AvailabilityApi
) {
    suspend fun registerAvailability(request: AvailabilityRequest): Response<Unit> {
        return api.registerAvailability(request)
    }
}*/
interface AvailabilityRepository {
    suspend fun registerAvailability(request: AvailabilityRequest): Int
}
