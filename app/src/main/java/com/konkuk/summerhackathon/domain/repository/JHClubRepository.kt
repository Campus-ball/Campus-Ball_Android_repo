package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.ClubListResponse
import javax.inject.Inject

interface ClubRepository {
    suspend fun getClubs(departmentId: Int): Result<ClubListResponse>
}