package com.konkuk.summerhackathon.presentation.clublookup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.summerhackathon.data.dto.response.CollegeListResponse
import com.konkuk.summerhackathon.data.dto.response.DepartmentListResponse
import com.konkuk.summerhackathon.domain.repository.CollegeRepository
import com.konkuk.summerhackathon.domain.repository.DepartmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DepartmentsViewModel @Inject constructor(
    private val collegeRepository: CollegeRepository,
    private val departmentRepository: DepartmentRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // 전체 학과 리스트 (대학 구분 없이 평탄화)
    private val _departments =
        MutableStateFlow<List<DepartmentListResponse.Department>>(emptyList())
    val departments: StateFlow<List<DepartmentListResponse.Department>> = _departments.asStateFlow()

    /** 1) 모든 대학 ID 조회 → 2) 각 ID로 학과 요청(병렬) → 3) 합쳐서 노출 */
    fun loadAllDepartments() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            // 1) 대학 목록
            val colleges: List<CollegeListResponse.College> = collegeRepository.getCollegeList()
                .getOrElse { e ->
                    _error.value = e.message ?: "대학 목록 로드 실패"
                    _isLoading.value = false
                    return@launch
                }.data.items

            // 2) 각 대학별 학과 병렬 요청
            val perCollege = colleges.map { college ->
                async {
                    departmentRepository.getDepartments(college.collegeId)
                        .getOrNull()
                        ?.data
                        ?.items
                        ?: emptyList()
                }
            }.awaitAll()

            // 3) 평탄화
            _departments.value = perCollege.flatten()
            _isLoading.value = false
        }
    }

    fun clearError() { _error.value = null }
}
