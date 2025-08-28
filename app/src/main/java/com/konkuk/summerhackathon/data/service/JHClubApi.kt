package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.ClubListResponse
import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ClubApi {
    @GET("api/club/{departmentId}/list")
    suspend fun getClubs(
        @Path("departmentId") departmentId: Int
    ): ClubListResponse
}