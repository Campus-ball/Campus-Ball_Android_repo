package com.konkuk.summerhackathon.presentation.auth.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.konkuk.summerhackathon.presentation.auth.screen.AuthConfig

class AuthViewModel : ViewModel() {
    var id by mutableStateOf("");        private set
    var pw by mutableStateOf("");        private set
    var idError by mutableStateOf<String?>(null);  private set
    var pwError by mutableStateOf<String?>(null);  private set
    var loginError by mutableStateOf<String?>(null); private set
    var isLoading by mutableStateOf(false); private set

    fun onIdChange(v: String) { id = v; idError = null; loginError = null }
    fun onPwChange(v: String) { pw = v; pwError = null; loginError = null }

    fun login(
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        idError = if (id.isBlank()) "아이디를 입력하세요" else null
        pwError = if (pw.isBlank()) "비밀번호를 입력하세요" else null
        if (idError != null || pwError != null) return

        isLoading = true
        val ok = (id == AuthConfig.DUMMY_ID && pw == AuthConfig.DUMMY_PW)
        isLoading = false
        if (ok) onSuccess()
        else {
            loginError = "입력하신 아이디 또는 비밀번호가 일치하지 않습니다."
            onFailure(loginError!!)
        }
    }
}