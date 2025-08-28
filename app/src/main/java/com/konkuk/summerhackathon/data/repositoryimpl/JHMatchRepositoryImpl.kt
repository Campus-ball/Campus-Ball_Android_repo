package com.konkuk.summerhackathon.data.repositoryimpl

import android.util.Log
import com.konkuk.summerhackathon.data.dto.request.MatchRequest
import com.konkuk.summerhackathon.data.dto.response.MatchResponse
import com.konkuk.summerhackathon.data.dto.response.ReceivedMatchResponse
import com.konkuk.summerhackathon.data.service.MatchApiService
import com.konkuk.summerhackathon.domain.repository.MatchRepository
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val matchApiService: MatchApiService
) : MatchRepository {
    // 일정 - 경기 성사 목록
    override suspend fun getMatchSuccessEvents(): Result<List<MatchResponse>> {
        return runCatching {
            val response = matchApiService.getMatchSuccessEvents()

            response.data.items
        }
    }

    // 제안 확인 - 받은 신청 목록
    override suspend fun getReceivedMatchSuccessEvents(): Result<List<ReceivedMatchResponse>> {
        return runCatching {
            val response = matchApiService.getReceivedMatchSuccessEvents()
            response.data.items
        }
    }

    override suspend fun acceptMatch(request: MatchRequest): Int {
        return try {
            val response = matchApiService.acceptMatch(request)
            response.code()
        } catch (e: Exception) {
            Log.e("MatchRepositoryImpl", "Error accepting match", e)
            -1
        }
    }

    override suspend fun rejectMatch(request: MatchRequest): Int {
        return try {
            val response = matchApiService.rejectMatch(request)
            response.code()
        } catch (e: Exception) {
            Log.e("MatchRepositoryImpl", "Error rejecting match", e)
            -1
        }
    }
}