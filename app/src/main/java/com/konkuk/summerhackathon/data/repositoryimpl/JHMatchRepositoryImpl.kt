package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.MatchResponse
import com.konkuk.summerhackathon.data.service.MatchApiService
import com.konkuk.summerhackathon.domain.repository.MatchRepository
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val matchApiService: MatchApiService
) : MatchRepository {
    override suspend fun getMatchSuccessEvents(): Result<List<MatchResponse>> {
        return runCatching {
            val response = matchApiService.getMatchSuccessEvents()

            response.data.items
        }
    }
}