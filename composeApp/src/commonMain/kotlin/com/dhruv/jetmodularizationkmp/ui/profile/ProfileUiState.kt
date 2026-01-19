package com.dhruv.jetmodularizationkmp.ui.profile

import com.dhruv.jetmodularizationkmp.domain.model.GithubRepo
import com.dhruv.jetmodularizationkmp.domain.model.GithubUser

sealed class ProfileUiState {
    object Idle : ProfileUiState()

    object Loading : ProfileUiState()

    data class Success(
        val user: GithubUser,
        val repos: List<GithubRepo>
    ) : ProfileUiState()

    data class Error(val message: String) : ProfileUiState()
}