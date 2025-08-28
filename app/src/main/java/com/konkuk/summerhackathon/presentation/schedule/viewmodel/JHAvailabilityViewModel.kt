package com.konkuk.summerhackathon.presentation.schedule.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.data.dto.request.AvailabilityRequest
import com.konkuk.summerhackathon.domain.repository.AvailabilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailabilityViewModel @Inject constructor (
    private val repository: AvailabilityRepository
) : ViewModel() {

    private val _statusCode = MutableStateFlow<Int?>(null)
    val statusCode: StateFlow<Int?> = _statusCode


    fun registerAvailability(request: AvailabilityRequest) {
        viewModelScope.launch {
            val code = repository.registerAvailability(request)
            Log.d("AvailabilityViewModel", "Received status code: $code")
            _statusCode.value = code
        }
    }
}