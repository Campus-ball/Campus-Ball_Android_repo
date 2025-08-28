package com.konkuk.summerhackathon.data.repositoryimpl

import com.konkuk.summerhackathon.data.dto.response.ProposalClubDetailResponse
import com.konkuk.summerhackathon.data.service.ProposalClubDetailApi
import com.konkuk.summerhackathon.domain.repository.ProposalDetailRepository
import javax.inject.Inject

class ProposalDetailRepositoryImpl @Inject constructor(
    private val apiService: ProposalClubDetailApi
) : ProposalDetailRepository {
    override suspend fun getProposalDetail(clubId: Int): Result<ProposalClubDetailResponse> {
        return runCatching {
            val response = apiService.getProposalClubDetail(clubId)
            if (response.status != 200) {
                throw Exception("API Error: ${response.message}")
            }

            response.data
        }
    }
}