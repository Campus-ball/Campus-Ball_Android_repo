package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse
import com.konkuk.summerhackathon.data.service.DepartmentApi
import com.konkuk.summerhackathon.domain.repository.DepartmentRepository
import javax.inject.Inject

class DepartmentRepositoryImpl @Inject constructor(
    private val api: DepartmentApi
) : DepartmentRepository {
    override suspend fun getDepartments(collegeId: Int): Result<DepartmentListResponse> =
        runCatching { api.getDepartments(collegeId) }
}