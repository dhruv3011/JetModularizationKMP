package com.dhruv.jetmodularizationkmp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.jetmodularizationkmp.domain.model.GithubUser
import com.dhruv.jetmodularizationkmp.domain.repository.GithubRepository
import com.dhruv.jetmodularizationkmp.domain.usecase.GetUserProfileUseCase
import com.dhruv.jetmodularizationkmp.domain.usecase.GetUserReposUseCase
import com.dhruv.jetmodularizationkmp.domain.utils.ServerResponse
import com.dhruv.jetmodularizationkmp.ui.profile.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GithubProfileViewModel (
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onSearchClicked(username: String) {
        if (username.isBlank()) return

        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            getUserProfileUseCase(username).collect { result ->
                when (result) {
                    is ServerResponse.Loading -> {
                        _uiState.value = ProfileUiState.Loading
                    }
                    is ServerResponse.Error -> {
                        _uiState.value = ProfileUiState.Error(result.message ?: "Unknown Error")
                    }
                    is ServerResponse.Success -> {
                        val user = result.data
                        if (user != null) {
                            fetchReposForUser(user)
                        } else {
                            _uiState.value = ProfileUiState.Error("User data is empty")
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchReposForUser(user: GithubUser) {
        getUserReposUseCase(user.login).collect { repoResponse ->
            when (repoResponse) {
                is ServerResponse.Success -> {
                    _uiState.value = ProfileUiState.Success(
                        user = user,
                        repos = repoResponse.data ?: emptyList()
                    )
                }
                is ServerResponse.Error -> {
                    _uiState.value = ProfileUiState.Success(
                        user = user,
                        repos = emptyList()
                    )
                }
                is ServerResponse.Loading -> {

                }
            }
        }
    }
}