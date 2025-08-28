package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.CollegeListResponse
import retrofit2.http.GET

interface CollegeApi {
    @GET("api/college/list")
    suspend fun getCollegeList(): CollegeListResponse
}