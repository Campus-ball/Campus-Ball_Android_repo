package com.konkuk.summerhackathon.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.domain.repository.DuplCheckRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DuplCheckViewModel(
    private val repository: DuplCheckRepository
) : ViewModel() {

    private val _isNicknameValid = MutableStateFlow<Boolean?>(null)
    val isNicknameValid: StateFlow<Boolean?> = _isNicknameValid

    private val _isUserIdValid = MutableStateFlow<Boolean?>(null)
    val isUserIdValid: StateFlow<Boolean?> = _isUserIdValid

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            val result = repository.checkNickname(nickname)
            result.onSuccess { valid ->
                _isNicknameValid.value = valid
                _errorMessage.value = null
            }.onFailure { throwable ->
                _isNicknameValid.value = null
                _errorMessage.value = throwable.message ?: "알 수 없는 오류가 발생했습니다."
            }
        }
    }

    fun checkUserId(userId: String) {
        viewModelScope.launch {
            val result = repository.checkUserId(userId)
            result.onSuccess { valid ->
                _isUserIdValid.value = valid
                _errorMessage.value = null
            }.onFailure { throwable ->
                _isUserIdValid.value = null
                _errorMessage.value = throwable.message ?: "알 수 없는 오류가 발생했습니다."
            }
        }
    }


}
