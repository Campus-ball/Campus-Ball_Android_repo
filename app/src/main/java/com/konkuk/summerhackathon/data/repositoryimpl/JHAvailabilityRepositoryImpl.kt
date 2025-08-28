package com.konkuk.summerhackathon.data.repositoryimpl

import android.util.Log
import com.konkuk.summerhackathon.data.dto.request.AvailabilityRequest
import com.konkuk.summerhackathon.data.service.AvailabilityApi
import com.konkuk.summerhackathon.domain.repository.AvailabilityRepository
import javax.inject.Inject

class AvailabilityRepositoryImpl @Inject constructor(
    private val api: AvailabilityApi
) : AvailabilityRepository {

    override suspend fun registerAvailability(request: AvailabilityRequest): Int {
        return try {
            val response = api.registerAvailability(request)
            response.code()
        } catch (e: Exception) {
            Log.e("AvailabilityRepositoryImpl", "Error registering availability", e)
            -1
        }
    }
}