package com.dhruv.jetmodularizationkmp.domain.usecase

import com.dhruv.jetmodularizationkmp.domain.model.GithubRepo
import com.dhruv.jetmodularizationkmp.domain.repository.GithubRepository
import com.dhruv.jetmodularizationkmp.domain.utils.ServerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserReposUseCase(
    private val repository: GithubRepository
) {
    operator fun invoke(userName: String): Flow<ServerResponse<List<GithubRepo>>> = flow {
        emit(ServerResponse.Loading())
        try {
            val repos = repository.getUserRepos(userName)
            emit(ServerResponse.Success(repos))
        } catch (e: Exception) {
            emit(ServerResponse.Error(e.message ?: "Could not fetch repositories"))
        }
    }
}