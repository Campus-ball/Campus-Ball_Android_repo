package com.konkuk.summerhackathon.presentation.clublookup.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.konkuk.summerhackathon.domain.repository.DepartmentRepository
import com.konkuk.summerhackathon.presentation.auth.viewmodel.SignUpViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ClubLookUpViewModel @Inject constructor(
    private val departmentRepository: DepartmentRepository
) : ViewModel() {
    companion object { private const val TAG = "ClubLookUpView" }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    val events: Flow<Event> = _events.receiveAsFlow()

    var universities by mutableStateOf(listOf<String>())
        private set

    var query by mutableStateOf("")
        private set

    init {
        loadUniversities()
    }

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }

    private fun loadUniversities() {
        universities = listOf(
            "건국대학교", "경희대학교", "고려대학교", "광운대학교", "국민대학교",
            "동국대학교", "서강대학교", "서울과학기술대학교", "서울대학교", "서울시립대학교",
            "성균관대학교", "세종대학교", "숙명여자대학교", "숭실대학교", "연세대학교",
            "이화여자대학교", "중앙대학교", "한성대학교", "한양대학교", "홍익대학교"
        )
    }
}
