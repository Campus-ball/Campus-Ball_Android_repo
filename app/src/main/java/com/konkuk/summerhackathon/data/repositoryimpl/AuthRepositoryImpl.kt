package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.request.ClubLeaderSignUpRequest
import com.konkuk.summerhackathon.data.dto.request.LoginRequest
import com.konkuk.summerhackathon.data.dto.response.ClubLeaderSignUpResponse
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

            if (res.status == 200 && res.data.accessToken.isNotBlank()) {
                val t = res.data
                tokenManager.saveTokens(t.accessToken, t.refreshToken, t.tokenType)
            } else {
                throw Exception(res.message.ifBlank { "잘못된 아이디 또는 비밀번호" })
            }
        }

    override suspend fun logout() {
        runCatching { api.logout() }
        tokenManager.clear()
    }

    override suspend fun signUpClubLeader(
        req: ClubLeaderSignUpRequest
    ): Result<ClubLeaderSignUpResponse> =
        runCatching { api.signUpClubLeader(req) }
}