package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.request.RefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.priorResponse != null) return null

        val refresh = tokenManager.refreshTokenBlocking() ?: return null

        val bundle = runBlocking {
            kotlin.runCatching { authApi.refresh(RefreshRequest(refresh)).data }.getOrNull()
        } ?: return null

        runBlocking {
            tokenManager.saveTokens(bundle.accessToken, bundle.refreshToken, bundle.tokenType)
        }

        val access = tokenManager.accessTokenBlocking() ?: return null
        val type = tokenManager.tokenTypeBlocking()

        return response.request.newBuilder()
            .header("Authorization", "${type.replaceFirstChar { it.uppercase() }} $access")
            .build()
    }
}
