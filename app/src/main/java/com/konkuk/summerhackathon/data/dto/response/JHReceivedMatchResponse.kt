package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceivedMatchResponse(
    @SerialName("requestId")
    val requestId: Int,
    @SerialName("requestType")
    val requestType: String,
    @SerialName("clubId")
    val clubId: Int,
    @SerialName("clubName")
    val clubName: String,
    @SerialName("departmentName")
    val departmentName: String,
    @SerialName("clubLogoUrl")
    val clubLogoUrl: String,
)

@Serializable
data class ReceivedMatchListResponse(
    @SerialName("items")
    val items: List<ReceivedMatchResponse>
)

@Serializable
data class BaseReceivedMatchResponse(
    @SerialName("status")
    val status: Int,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: ReceivedMatchListResponse
)