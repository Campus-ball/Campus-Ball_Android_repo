package com.konkuk.summerhackathon.presentation.match.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.data.dto.request.RandomMatchRequest
import com.konkuk.summerhackathon.data.dto.response.RandomMatchRequestResponse
import com.konkuk.summerhackathon.data.dto.response.RandomMatchResponse
import com.konkuk.summerhackathon.domain.repository.MatchingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _events = MutableSharedFlow<Event>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<Event> = _events

    fun fetchRandomMatch() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            matchRepository.getRandomMatch()
                .onSuccess { res ->
                    _ui.value = res.data.toUi()
                    _events.tryEmit(Event.MatchFetched(res.message))
                }
                .onFailure { e ->
                    _error.value = e.message ?: "매칭 정보를 불러오지 못했습니다."
                    _events.tryEmit(Event.Error(_error.value!!))
                }

            _isLoading.value = false
        }
    }

    fun requestMatch() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val req = RandomMatchRequest(
                clubId = _ui.value.clubId,
                startDate = _ui.value.startDate,
                startTime = _ui.value.startTime
            )
            Log.d(TAG, "POST /match/random/request body=$req")

            matchRepository.requestRandomMatch(req)
                .onSuccess { res ->
                    Log.d(TAG, "requestMatch success: $res")
                    _events.tryEmit(Event.RequestSuccess(res))
                }
                .onFailure { e ->
                    Log.e(TAG, "requestMatch failure", e)
                    _error.value = e.message ?: "매치 제안에 실패했습니다."
                    _events.tryEmit(Event.Error(_error.value!!))
                }

            _isLoading.value = false
        }
    }

    sealed interface Event {
        data class MatchFetched(val message: String) : Event
        data class RequestSuccess(val res: RandomMatchRequestResponse) : Event
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
