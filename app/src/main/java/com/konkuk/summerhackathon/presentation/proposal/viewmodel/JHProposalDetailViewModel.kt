package com.konkuk.summerhackathon.presentation.proposal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.data.dto.response.ProposalClubDetailResponse
import com.konkuk.summerhackathon.data.dto.response.ReceivedMatchResponse
import com.konkuk.summerhackathon.domain.repository.MatchRepository
import com.konkuk.summerhackathon.domain.repository.ProposalDetailRepository
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
class ProposalDetailViewModel @Inject constructor(
    private val repository: ProposalDetailRepository
) : ViewModel() {

    private val _events = MutableStateFlow<ProposalClubDetailResponse?>(null)
    val events: StateFlow<ProposalClubDetailResponse?> = _events.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    fun fetchProposalDetail(clubId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getProposalDetail(clubId)
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
}