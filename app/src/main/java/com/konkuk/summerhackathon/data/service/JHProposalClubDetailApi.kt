package com.konkuk.summerhackathon.data.service

import com.konkuk.summerhackathon.data.dto.response.BaseProposalClubDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProposalClubDetailApi {
    @GET("/club/{clubId}")
    suspend fun getProposalClubDetail(
        @Path("clubId") clubId: Int
    ): BaseProposalClubDetailResponse
}