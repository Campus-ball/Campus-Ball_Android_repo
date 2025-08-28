package com.konkuk.summerhackathon.data.service

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val path = req.url.encodedPath

        val noAuth = path.contains("/auth/login") || path.contains("/auth/refresh")

        val access = tokenManager.accessTokenBlocking()
        val type = tokenManager.tokenTypeBlocking()

        val newReq = if (!noAuth && !access.isNullOrBlank()) {
            req.newBuilder()
                .addHeader("Authorization", "${type.replaceFirstChar { it.uppercase() }} $access")
                .build()
        } else req

        return chain.proceed(newReq)
    }
}