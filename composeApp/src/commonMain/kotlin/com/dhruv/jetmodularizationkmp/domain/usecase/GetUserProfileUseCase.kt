package com.dhruv.jetmodularizationkmp.domain.usecase

import com.dhruv.jetmodularizationkmp.domain.model.GithubUser
import com.dhruv.jetmodularizationkmp.domain.repository.GithubRepository
import com.dhruv.jetmodularizationkmp.domain.utils.ServerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserProfileUseCase(
    private val repository: GithubRepository
) {
    operator fun invoke(userName: String): Flow<ServerResponse<GithubUser>> = flow {
        emit(ServerResponse.Loading())
        try {
            val user = repository.getUserProfile(userName)
            emit(ServerResponse.Success(user))
        } catch (e: Exception) {
            emit(ServerResponse.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}