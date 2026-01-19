package com.dhruv.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dhruv.jetmodularizationkmp.ui.components.RoundedTextField
import com.dhruv.jetmodularizationkmp.ui.profile.ProfileUiState
import com.dhruv.jetmodularizationkmp.ui.viewmodel.GithubProfileViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ProfileScreen(
    viewModel: GithubProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()
            .clickable { focusRequester.requestFocus() },
            verticalAlignment = Alignment.CenterVertically) {
            RoundedTextField(
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(focusRequester),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                labelText = "Search UserName",
                leadingIcon = Icons.Default.Search,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
                keyboardActions = KeyboardActions (
                    onSearch =  {
                        if (searchQuery.isNotBlank()) {
                            viewModel.onSearchClicked(searchQuery)
                            keyboardController?.hide()
                        }
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. STATE HANDLING ---
        when (val state = uiState) {
            is ProfileUiState.Idle -> {
                Text("Search for a user to see their profile", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is ProfileUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is ProfileUiState.Error -> {
                Text(text = state.message, color = Color.Red)
            }
            is ProfileUiState.Success -> {
                ProfileResultContent(user = state.user, repos = state.repos)
            }
        }
    }
}