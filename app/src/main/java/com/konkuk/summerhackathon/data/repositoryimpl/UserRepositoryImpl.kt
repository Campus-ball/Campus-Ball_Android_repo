package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.UserMeResponse
import com.konkuk.summerhackathon.data.service.UserApi
import com.konkuk.summerhackathon.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {

    override suspend fun getMe(): Result<UserMeResponse> =
        runCatching { api.getMe() }
}