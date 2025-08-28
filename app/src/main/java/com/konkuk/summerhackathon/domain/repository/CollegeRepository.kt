package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.CollegeListResponse

interface CollegeRepository {
    suspend fun getCollegeList(): Result<CollegeListResponse>
}