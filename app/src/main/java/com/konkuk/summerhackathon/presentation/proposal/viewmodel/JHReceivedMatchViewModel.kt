package com.konkuk.summerhackathon.presentation.proposal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.data.dto.request.MatchRequest
import com.konkuk.summerhackathon.data.dto.request.MatchSendRequest
import com.konkuk.summerhackathon.data.dto.response.ReceivedMatchResponse
import com.konkuk.summerhackathon.domain.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReceivedMatchViewModel @Inject constructor(
    private val repository: MatchRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<ReceivedMatchResponse>>(emptyList())
    val events: StateFlow<List<ReceivedMatchResponse>> = _events.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    private val _actionResult = MutableSharedFlow<Boolean>()
    val actionResult: SharedFlow<Boolean> = _actionResult.asSharedFlow()

    init {
        fetchMatchProposals()
    }

    fun fetchMatchProposals() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getReceivedMatchSuccessEvents()
                    .onSuccess { fetchedEvents ->
                        _events.value = fetchedEvents
                        Log.d("MatchViewModel", "Fetched events: $fetchedEvents")
                    }
                    .onFailure { throwable ->
                        val errorMessage = throwable.message ?: "알 수 없는 오류가 발생했습니다."
                        _error.emit(errorMessage)
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun acceptMatch(request: MatchRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val code = repository.acceptMatch(request)
                if (code == 200) {
                    _actionResult.emit(true)
                    fetchMatchProposals() // 새로고침
                } else {
                    _error.emit("매치 수락 실패 (code: $code)")
                    _actionResult.emit(false)
                }
            } catch (e: Exception) {
                _error.emit("매치 수락 에러: ${e.message}")
                _actionResult.emit(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun rejectMatch(request: MatchRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val code = repository.rejectMatch(request)
                if (code == 200) {
                    _actionResult.emit(true)
                    fetchMatchProposals() // 새로고침
                } else {
                    _error.emit("매치 거절 실패 (code: $code)")
                    _actionResult.emit(false)
                }
            } catch (e: Exception) {
                _error.emit("매치 거절 에러: ${e.message}")
                _actionResult.emit(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 친선 경기 신청 보내기
    fun sendMatch(request: MatchSendRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val code = repository.sendMatch(request)
                if (code == 200) {
                    _actionResult.emit(true)
                    fetchMatchProposals() // 새로고침
                } else {
                    _error.emit("친선 경기 신청 실패 (code: $code)")
                    _actionResult.emit(false)
                }
            } catch (e: Exception) {
                _error.emit("친선 경기 신청 에러: ${e.message}")
                _actionResult.emit(false)
            } finally {
                _isLoading.value = false
            }
        }
    }


}