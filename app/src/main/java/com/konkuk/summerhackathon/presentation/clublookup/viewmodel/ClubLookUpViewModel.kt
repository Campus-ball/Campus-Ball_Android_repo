package com.konkuk.summerhackathon.presentation.clublookup.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClubLookUpViewModel @Inject constructor(
) : ViewModel() {

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
