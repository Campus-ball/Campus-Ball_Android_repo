package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DepartmentApi {
    @GET("api/department/{collegeId}/list")
    suspend fun getDepartments(
        @Path("collegeId") collegeId: Int
    ): DepartmentListResponse
}