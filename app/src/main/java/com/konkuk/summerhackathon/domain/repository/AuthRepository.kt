package com.konkuk.summerhackathon.domain.repository

interface AuthRepository {
    suspend fun login(userId: String, password: String): Result<Unit>
    suspend fun logout()
}