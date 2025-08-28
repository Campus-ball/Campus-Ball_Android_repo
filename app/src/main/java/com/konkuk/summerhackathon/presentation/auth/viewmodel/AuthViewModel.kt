package com.konkuk.summerhackathon.presentation.auth.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.presentation.auth.screen.AuthConfig
import com.konkuk.summerhackathon.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

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

        // 서버 연결 전
        /*
        try {
            val ok = (id == AuthConfig.DUMMY_ID && pw == AuthConfig.DUMMY_PW)
            if (ok) {
                loginError = null
                onSuccess()
            } else {
                loginError = "잘못된 형식의 아이디 또는 비밀번호 입니다"
                id = ""
                pw = ""
                onFailure(loginError!!)
            }
        } finally {
            isLoading = false
        }
        */
        // ===============================================================

        // 서버 연결 후
        viewModelScope.launch {
            val result = authRepository.login(id, pw)
            isLoading = false
            if (result.isSuccess) {
                loginError = null
                onSuccess()
            } else {
                loginError = "잘못된 형식의 아이디 또는 비밀번호 입니다"
                id = ""
                pw = ""
                onFailure(loginError!!)
            }
        }
        // ===============================================================
    }

    fun logout() {
        viewModelScope.launch { authRepository.logout() }
    }
}
