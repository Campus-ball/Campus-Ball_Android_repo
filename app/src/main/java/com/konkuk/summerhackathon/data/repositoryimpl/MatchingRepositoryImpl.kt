package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.request.RandomMatchRequest
import com.konkuk.summerhackathon.data.dto.response.RandomMatchRequestResponse
import com.konkuk.summerhackathon.data.dto.response.RandomMatchResponse
import com.konkuk.summerhackathon.data.service.MatchApi
import com.konkuk.summerhackathon.domain.repository.MatchingRepository
import javax.inject.Inject

class MatchingRepositoryImpl @Inject constructor(
    private val api: MatchApi
) : MatchingRepository {

    override suspend fun getRandomMatch(): Result<RandomMatchResponse> =
        runCatching { api.getRandomMatch() }

    override suspend fun requestRandomMatch(
        body: RandomMatchRequest
    ): Result<RandomMatchRequestResponse> =
        runCatching { api.requestRandomMatch(body) }
}