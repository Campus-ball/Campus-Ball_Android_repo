package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.CollegeListResponse
import com.konkuk.summerhackathon.data.service.CollegeApi
import com.konkuk.summerhackathon.domain.repository.CollegeRepository
import javax.inject.Inject

class CollegeRepositoryImpl @Inject constructor(
    private val api: CollegeApi
) : CollegeRepository {
    override suspend fun getCollegeList(): Result<CollegeListResponse> =
        runCatching { api.getCollegeList() }
}