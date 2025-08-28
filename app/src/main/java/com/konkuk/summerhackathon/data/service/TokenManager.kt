package com.konkuk.summerhackathon.data.service

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val KEY_ACCESS = stringPreferencesKey("access_token")
    private val KEY_REFRESH = stringPreferencesKey("refresh_token")
    private val KEY_TYPE = stringPreferencesKey("token_type")

    @Volatile private var cachedAccess: String? = null
    @Volatile private var cachedRefresh: String? = null
    @Volatile private var cachedType: String? = null

    suspend fun saveTokens(access: String, refresh: String, type: String) {
        cachedAccess = access; cachedRefresh = refresh; cachedType = type
        context.dataStore.edit {
            it[KEY_ACCESS] = access
            it[KEY_REFRESH] = refresh
            it[KEY_TYPE] = type
        }
    }

    suspend fun clear() {
        cachedAccess = null; cachedRefresh = null; cachedType = null
        context.dataStore.edit { it.clear() }
    }

    suspend fun accessToken(): String? =
        cachedAccess ?: context.dataStore.data.map { it[KEY_ACCESS] }.first()

    suspend fun refreshToken(): String? =
        cachedRefresh ?: context.dataStore.data.map { it[KEY_REFRESH] }.first()

    suspend fun tokenType(): String =
        cachedType ?: context.dataStore.data.map { it[KEY_TYPE] ?: "bearer" }.first()

    fun accessTokenBlocking(): String? = cachedAccess
    fun refreshTokenBlocking(): String? = cachedRefresh
    fun tokenTypeBlocking(): String = cachedType ?: "bearer"
}
