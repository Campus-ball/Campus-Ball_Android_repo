package com.konkuk.summerhackathon.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 최상위 dto
@Serializable
data class BaseProposalClubDetailResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: ProposalClubDetailResponse
)

@Serializable
data class ProposalClubDetailResponse(
    @SerialName("opponent")
    val opponent: ProposalOpponentResponse,
    @SerialName("myCalendar")
    val myCalendar: ProposalCalendarListResponse,
    @SerialName("opponentCalendar")
    val opponentCalendar: ProposalCalendarListResponse
)

@Serializable
data class ProposalOpponentResponse(
    @SerialName("clubId")
    val clubId: Int,
    @SerialName("clubName")
    val clubName: String,
    @SerialName("departmentName")
    val departmentName: String,
    @SerialName("clubLogoUrl")
    val clubLogoUrl: String,
    @SerialName("clubDescription")
    val clubDescription: String
)

@Serializable
data class ProposalCalendarListResponse(
    @SerialName("items")
    val items: List<CalendarEventDto>
)
