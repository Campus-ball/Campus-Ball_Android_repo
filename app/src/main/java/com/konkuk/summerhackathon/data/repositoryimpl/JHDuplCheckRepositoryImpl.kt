package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.service.DuplCheckApi
import com.konkuk.summerhackathon.domain.repository.DuplCheckRepository


class DuplCheckRepositoryImpl(
    private val api: DuplCheckApi
) : DuplCheckRepository {

    override suspend fun checkNickname(nickname: String): Result<Boolean> {
        return try {
            val response = api.checkNickname(nickname)
            Result.success(response.data.isValid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}

