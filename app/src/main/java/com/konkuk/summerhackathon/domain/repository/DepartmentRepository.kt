package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse

interface DepartmentRepository {
    suspend fun getDepartments(collegeId: Int): Result<DepartmentListResponse>
}