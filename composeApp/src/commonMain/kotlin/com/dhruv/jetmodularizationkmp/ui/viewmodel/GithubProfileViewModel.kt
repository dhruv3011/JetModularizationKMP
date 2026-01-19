package com.dhruv.jetmodularizationkmp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruv.jetmodularizationkmp.domain.model.GithubUser
import com.dhruv.jetmodularizationkmp.domain.repository.GithubRepository
import com.dhruv.jetmodularizationkmp.ui.profile.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GithubProfileViewModel (
    private val repository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onSearchClicked(username: String) {
        if (username.isBlank()) return

        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            val user = repository.getUserProfile(username)

            if (user == null) {
                _uiState.value = ProfileUiState.Error("User not found or network error")
                return@launch
            }

            val repos = repository.getUserRepos(username)

            _uiState.value = ProfileUiState.Success(user, repos)
        }
    }
}