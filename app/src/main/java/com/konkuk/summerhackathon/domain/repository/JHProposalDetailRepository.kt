package com.konkuk.summerhackathon.domain.repository

import com.konkuk.summerhackathon.data.dto.response.ProposalClubDetailResponse

interface ProposalDetailRepository {
    suspend fun getProposalDetail(clubId: Int): Result<ProposalClubDetailResponse>

}
