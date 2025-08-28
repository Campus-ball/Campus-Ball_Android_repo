package com.konkuk.summerhackathon.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ClubLeaderSignUpRequest(
    val name: String,
    val nickname: String,
    val gender: String,         // "MALE"/"FEMALE"
    val userId: String,
    val password: String,
    val clubName: String,
    val clubDescription: String? = null,  // ⬅️ nullable
    val collegeId: Int,
    val departmentId: Int,
    val phoneNumber: String,
    val clubLogoUrl: String? = null,      // ⬅️ nullable
    val chatUrl: String? = null           // ⬅️ nullable
)