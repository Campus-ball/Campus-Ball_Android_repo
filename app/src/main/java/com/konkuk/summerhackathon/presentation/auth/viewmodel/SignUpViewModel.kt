package com.konkuk.summerhackathon.presentation.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.core.component.Gender
import com.konkuk.summerhackathon.data.dto.request.ClubLeaderSignUpRequest
import com.konkuk.summerhackathon.data.dto.response.ClubLeaderSignUpResponse
import com.konkuk.summerhackathon.domain.repository.AuthRepository
import com.konkuk.summerhackathon.presentation.auth.component.LeaderSignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    companion object { private const val TAG = "SignUpView" }

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    val events: Flow<Event> = _events.receiveAsFlow()

    fun signUpLeader(data: LeaderSignUpData) {
        viewModelScope.launch {
            _isLoading.value = true
            val req = data.toRequest(
                collegeId = mapCollegeNameToId(data.university),
                departmentId = mapDepartmentNameToId(data.department)
            )
            Log.d(TAG, "POST /auth/signup/club-leader body=$req")

            authRepository.signUpClubLeader(req)
                .onSuccess { res ->
                    Log.d(TAG, "signUp success: $res")
                    _events.send(Event.SignUpLeaderSuccess(res))
                }
                .onFailure { e ->
                    Log.e(TAG, "signUp failure", e)
                    _events.send(Event.Error(e.message ?: "회원가입에 실패했습니다."))
                }

            _isLoading.value = false
        }
    }

    // TODO: 서버 기준으로 실제 매핑으로 교체
    private fun mapCollegeNameToId(name: String) = when (name) {
        "건국대학교" -> 12 else -> 0
    }
    private fun mapDepartmentNameToId(name: String) = when (name) {
        "컴퓨터공학부","컴퓨터과학과","소프트웨어학과","컴퓨터학과" -> 22
        else -> 0
    }

    sealed interface Event {
        data class SignUpLeaderSuccess(val res: ClubLeaderSignUpResponse): Event
        data class Error(val message: String): Event
    }
}

private fun LeaderSignUpData.toRequest(
    collegeId: Int,
    departmentId: Int
): ClubLeaderSignUpRequest = ClubLeaderSignUpRequest(
    name = name,
    nickname = nickname,
    gender = if (gender == Gender.MALE) "MALE" else "FEMALE",
    userId = userId,
    password = password,
    clubName = clubName,
    clubDescription = clubIntro,
    collegeId = collegeId,
    departmentId = departmentId,
    phoneNumber = contact,
    clubLogoUrl = clubLogoUri ?: "",
    chatUrl = kakaoOpenChatLink
)

