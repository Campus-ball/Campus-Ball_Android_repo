package com.konkuk.summerhackathon.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.domain.repository.DuplCheckRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// UI 표시용 상태
sealed class CheckState {
    data object Idle : CheckState()
    data object Checking : CheckState()
    data object Available : CheckState()
    data class Unavailable(val reason: String) : CheckState()
    data class Error(val message: String) : CheckState()
}

@HiltViewModel
class DuplCheckViewModel @Inject constructor(
    private val repository: DuplCheckRepository
) : ViewModel() {

    // --- 기존 공개 필드(호환 유지) ---
    private val _isNicknameValid = MutableStateFlow<Boolean?>(null)
    val isNicknameValid: StateFlow<Boolean?> = _isNicknameValid

    private val _isUserIdValid = MutableStateFlow<Boolean?>(null)
    val isUserIdValid: StateFlow<Boolean?> = _isUserIdValid

    // 단일 에러 메시지는 충돌이 있어 필드별 상태와 함께만 보조적으로 유지
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // --- 신규: 필드별 CheckState ---
    private val _nicknameState = MutableStateFlow<CheckState>(CheckState.Idle)
    val nicknameState: StateFlow<CheckState> = _nicknameState

    private val _userIdState = MutableStateFlow<CheckState>(CheckState.Idle)
    val userIdState: StateFlow<CheckState> = _userIdState

    fun checkNickname(nickname: String) {
        // 클릭 즉시 "확인중…" -> 빠른 재컴포지션
        _nicknameState.value = CheckState.Checking
        _errorMessage.value = null
        viewModelScope.launch {
            val result = repository.checkNickname(nickname)
            result.onSuccess { valid ->
                _isNicknameValid.value = valid
                _nicknameState.value = if (valid) {
                    CheckState.Available
                } else {
                    CheckState.Unavailable("닉네임 중복 입니다")
                }
            }.onFailure { throwable ->
                _isNicknameValid.value = null
                val msg = throwable.message ?: "알 수 없는 오류가 발생했습니다."
                _errorMessage.value = msg
                _nicknameState.value = CheckState.Error(msg)
            }
        }
    }

    fun checkUserId(userId: String) {
        _userIdState.value = CheckState.Checking
        _errorMessage.value = null
        viewModelScope.launch {
            val result = repository.checkUserId(userId)
            result.onSuccess { valid ->
                _isUserIdValid.value = valid
                _userIdState.value = if (valid) {
                    CheckState.Available
                } else {
                    CheckState.Unavailable("아이디 중복 입니다")
                }
            }.onFailure { throwable ->
                _isUserIdValid.value = null
                val msg = throwable.message ?: "알 수 없는 오류가 발생했습니다."
                _errorMessage.value = msg
                _userIdState.value = CheckState.Error(msg)
            }
        }
    }

    // 입력 변경 시 호출: 즉시 Idle 로 돌려 UI를 초기화(바로 재컴포지션)
    fun resetNickname() {
        _isNicknameValid.value = null
        _errorMessage.value = null
        _nicknameState.value = CheckState.Idle
    }

    fun resetUserId() {
        _isUserIdValid.value = null
        _errorMessage.value = null
        _userIdState.value = CheckState.Idle
    }
}
