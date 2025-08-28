package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.ClubListResponse
import com.konkuk.summerhackathon.data.service.ClubApi
import com.konkuk.summerhackathon.domain.repository.ClubRepository
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val api: ClubApi
) : ClubRepository {
    override suspend fun getClubs(departmentId: Int): Result<ClubListResponse> =
        runCatching { api.getClubs(departmentId) }
}