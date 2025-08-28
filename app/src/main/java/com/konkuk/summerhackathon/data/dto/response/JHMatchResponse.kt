package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchResponse(
    @SerialName("matchId")
    val matchId: Int,
    @SerialName("matchType")
    val matchType: String,
    @SerialName("clubId")
    val clubId: Int,
    @SerialName("clubName")
    val clubName: String,
    @SerialName("departmentName")
    val departmentName: String,
    @SerialName("clubLogoUrl")
    val clubLogoUrl: String,
    @SerialName("chatUrl")
    val chatUrl: String
)

@Serializable
data class MatchListResponse(
    @SerialName("items")
    val items: List<MatchResponse>
)

@Serializable
data class BaseMatchResponse(
    @SerialName("status")
    val status: Int,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: MatchListResponse
)