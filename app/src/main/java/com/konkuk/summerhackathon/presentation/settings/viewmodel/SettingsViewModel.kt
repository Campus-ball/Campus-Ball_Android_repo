package com.konkuk.summerhackathon.presentation.settings.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.domain.repository.UserRepository
import com.konkuk.summerhackathon.data.dto.response.UserMeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyAccountUi(
    val club: String = "",
    val name: String = "",
    val nickname: String = "",
    val gender: String = "",
    val contact: String = "",
    val kakaoOpenChat: String = ""
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(MyAccountUi())
    val ui = _ui.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        Log.d("SettingsViewModel", "ViewModel created")
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            Log.d("SettingsViewModel", "refresh() start, thread=${Thread.currentThread().name}")
            _isLoading.value = true
            _error.value = null

            val t0 = System.currentTimeMillis()
            userRepository.getMe()
                .onSuccess { res ->
                    Log.d("SettingsViewModel", "getMe() success in ${System.currentTimeMillis()-t0}ms")
                    Log.d("SettingsViewModel", "response=$res")
                    _ui.value = res.toUi()
                    Log.d("SettingsViewModel", "ui mapped=${_ui.value}")
                }
                .onFailure { e ->
                    Log.e("SettingsViewModel", "getMe() failure in ${System.currentTimeMillis()-t0}ms", e)
                    _error.value = e.message ?: "알 수 없는 오류"
                }

            _isLoading.value = false
            Log.d("SettingsViewModel", "refresh() end")
        }
    }
}

private fun UserMeResponse.toUi(): MyAccountUi {
    val d: UserMeResponse.Data = this.data
    return MyAccountUi(
        club = d.clubName,
        name = d.name,
        nickname = d.nickname,
        gender = d.gender,
        contact = d.phoneNumber,
        kakaoOpenChat = d.chatUrl
    )
}
