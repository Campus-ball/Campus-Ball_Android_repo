package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DepartmentListResponse(
    val status: Int,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val items: List<Department>
    )

    @Serializable
    data class Department(
        val departmentId: Int,
        val departmentName: String
    )
}
