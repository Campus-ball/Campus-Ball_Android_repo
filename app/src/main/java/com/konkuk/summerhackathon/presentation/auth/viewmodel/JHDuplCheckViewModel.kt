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

    private val _isValid = MutableStateFlow<Boolean?>(null)
    val isValid: StateFlow<Boolean?> = _isValid

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            val result = repository.checkNickname(nickname)
            result.onSuccess { valid ->
                _isValid.value = valid
                _errorMessage.value = null
            }.onFailure { throwable ->
                _isValid.value = null
                _errorMessage.value = throwable.message ?: "알 수 없는 오류가 발생했습니다."
            }
        }
    }


}
