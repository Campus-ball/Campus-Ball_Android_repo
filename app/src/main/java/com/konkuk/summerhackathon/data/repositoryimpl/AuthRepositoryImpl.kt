package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.request.LoginRequest
import com.konkuk.summerhackathon.data.service.AuthApi
import com.konkuk.summerhackathon.data.service.TokenManager
import com.konkuk.summerhackathon.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(userId: String, password: String): Result<Unit> =
        runCatching {
            val res = api.login(LoginRequest(userId, password))
            val t = res.data
            tokenManager.saveTokens(t.accessToken, t.refreshToken, t.tokenType)
        }

    override suspend fun logout() {
        tokenManager.clear()
    }
}