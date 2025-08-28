package com.konkuk.summerhackathon.presentation.match.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.data.dto.response.RandomMatchRequestResponse
import com.konkuk.summerhackathon.data.dto.response.RandomMatchResponse
import com.konkuk.summerhackathon.domain.repository.MatchingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchUi(
    val clubId: Long = 0L,
    val clubName: String = "",
    val departmentName: String = "",
    val clubLogoUrl: String = "",
    val clubDescription: String = "",
    val startDate: String = "",
    val startTime: String = ""
)

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchRepository: MatchingRepository
) : ViewModel() {

    companion object { private const val TAG = "MatchViewModel" }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _ui = MutableStateFlow(MatchUi())
    val ui: StateFlow<MatchUi> = _ui.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    val events: Flow<Event> = _events.receiveAsFlow()

    fun fetchRandomMatch() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            matchRepository.getRandomMatch()
                .onSuccess { res ->
                    _ui.value = res.data.toUi()
                    _events.send(Event.MatchFetched(res.message))
                }
                .onFailure { e ->
                    _error.value = e.message ?: "매칭 정보를 불러오지 못했습니다."
                    _events.send(Event.Error(_error.value!!))
                }
            _isLoading.value = false
        }
    }

    /** 제안하기 */
    fun requestMatch() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            matchRepository.requestRandomMatch()
                .onSuccess { res ->
                    // 필요 시 상태 저장
                    _events.send(Event.RequestSuccess(res))   // ⬅️ 이 타입이 아래에 정의되어 있어야 함
                }
                .onFailure { e ->
                    _error.value = e.message ?: "매치 제안에 실패했습니다."
                    _events.send(Event.Error(_error.value!!))
                }
            _isLoading.value = false
        }
    }

    sealed interface Event {
        data class MatchFetched(val message: String) : Event
        data class RequestSuccess(
            val res: com.konkuk.summerhackathon.data.dto.response.RandomMatchRequestResponse
        ) : Event
        data class Error(val message: String) : Event
    }
}

private fun RandomMatchResponse.Data.toUi() =
    MatchUi(
        clubId = clubId,
        clubName = clubName,
        departmentName = departmentName,
        clubLogoUrl = clubLogoUrl,
        clubDescription = clubDescription,
        startDate = startDate,
        startTime = startTime
    )
