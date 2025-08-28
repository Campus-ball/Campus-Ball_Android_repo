package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.UserMeResponse

interface UserRepository {
    suspend fun getMe(): Result<UserMeResponse>
}