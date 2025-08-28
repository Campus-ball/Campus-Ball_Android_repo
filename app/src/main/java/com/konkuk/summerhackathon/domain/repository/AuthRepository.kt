package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.request.ClubLeaderSignUpRequest
import com.konkuk.summerhackathon.data.dto.response.ClubLeaderSignUpResponse

interface AuthRepository {
    suspend fun login(userId: String, password: String): Result<Unit>

    suspend fun logout()

    suspend fun signUpClubLeader(req: ClubLeaderSignUpRequest): Result<ClubLeaderSignUpResponse>
}