package com.konkuk.summerhackathon.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 수락, 거절 위한 api
@Serializable
data class MatchRequest(
    @SerialName("requestId")
    val requestId: Int
)

// 친선 경기 신청 위한 api
@Serializable
data class MatchSendRequest(
    @SerialName("clubId")
    val clubId: Int
)
