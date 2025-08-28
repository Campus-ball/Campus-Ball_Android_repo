package com.konkuk.summerhackathon.domain.repository

interface DuplCheckRepository {
    suspend fun checkNickname(nickname: String): Result<Boolean>
    suspend fun checkUserId(userId: String): Result<Boolean>
}