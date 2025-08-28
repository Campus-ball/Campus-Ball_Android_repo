package com.konkuk.summerhackathon.presentation.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.core.component.Gender
import com.konkuk.summerhackathon.data.dto.request.ClubLeaderSignUpRequest
import com.konkuk.summerhackathon.data.dto.response.ClubLeaderSignUpResponse
import com.konkuk.summerhackathon.data.dto.response.CollegeListResponse
import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse
import com.konkuk.summerhackathon.domain.repository.AuthRepository
import com.konkuk.summerhackathon.domain.repository.CollegeRepository
import com.konkuk.summerhackathon.domain.repository.DepartmentRepository
import com.konkuk.summerhackathon.presentation.auth.component.LeaderSignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val collegeRepository: CollegeRepository,
    private val departmentRepository: DepartmentRepository
) : ViewModel() {

    companion object { private const val TAG = "SignUpView" }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    val events: Flow<Event> = _events.receiveAsFlow()

    // 대학 리스트 상태
    private val _colleges = MutableStateFlow<List<CollegeListResponse.College>>(emptyList())
    val colleges: StateFlow<List<CollegeListResponse.College>> = _colleges.asStateFlow()

    // 학과 (선택된 대학 기준)
    private val _departments =
        MutableStateFlow<List<DepartmentListResponse.Department>>(emptyList())
    val departments: StateFlow<List<DepartmentListResponse.Department>> = _departments.asStateFlow()


    init {
        loadColleges()
    }

    fun loadColleges() {
        viewModelScope.launch {
            collegeRepository.getCollegeList()
                .onSuccess { res ->
                    _colleges.value = res.data.items
                    Log.d(TAG, "colleges loaded: ${res.data.items.size}")
                }
                .onFailure { e ->
                    Log.e(TAG, "college list error", e)
                }
        }
    }

    /** 대학 선택 시 호출 → 해당 대학 학과 로드 */
    fun loadDepartments(collegeId: Int) = viewModelScope.launch {
        _departments.value = emptyList()
        departmentRepository.getDepartments(collegeId)
            .onSuccess { res -> _departments.value = res.data.items }
            .onFailure { e -> Log.e(TAG, "department list error", e) }
    }

    /** 가입 */
    fun signUpLeader(data: LeaderSignUpData) = viewModelScope.launch {
        _isLoading.value = true

        val collegeId = _colleges.value.find { it.collegeName == data.university }?.collegeId ?: 0
        // departmentName은 서버에서 전체 이름(예: "서울대학교 컴퓨터공학부")로 내려오므로 그대로 매칭
        val departmentId = _departments.value
            .find { it.departmentName == data.department }
            ?.departmentId

        val req = data.toRequest(
            collegeId = collegeId,
            departmentId = departmentId ?: 0 // 서버가 null 허용이면 DTO/필드를 Int?로 바꿔 null 전송
        ).normalizeOptionals()

        Log.d(TAG, "POST /auth/signup/club-leader body=$req")

        authRepository.signUpClubLeader(req)
            .onSuccess { res -> _events.send(Event.SignUpLeaderSuccess(res)) }
            .onFailure { e ->
                Log.e(TAG, "signUp failure", e)
                _events.send(Event.Error(e.message ?: "회원가입에 실패했습니다."))
            }

        _isLoading.value = false
    }

    sealed interface Event {
        data class SignUpLeaderSuccess(val res: ClubLeaderSignUpResponse): Event
        data class Error(val message: String): Event
    }
}

/** UI -> API 변환 (성별 매핑 포함) */
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
    clubDescription = clubIntro,   // 빈 문자열은 아래 normalize에서 null 처리 가능
    collegeId = collegeId,
    departmentId = departmentId,
    phoneNumber = contact,
    clubLogoUrl = clubLogoUri ?: "",
    chatUrl = kakaoOpenChatLink
)

/** 선택/선택적 필드 정리: 빈 문자열은 null로 바꿔 키를 생략하고 싶을 때 사용 */
private fun ClubLeaderSignUpRequest.normalizeOptionals(): ClubLeaderSignUpRequest =
    copy(
        clubDescription = clubDescription?.takeIf { it.isNotBlank() },
        clubLogoUrl = clubLogoUrl?.takeIf { it.isNotBlank() },
        chatUrl = chatUrl?.takeIf { it.isNotBlank() }
    )
