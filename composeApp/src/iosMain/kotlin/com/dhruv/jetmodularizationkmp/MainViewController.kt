package com.dhruv.jetmodularizationkmp

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController (
    configure =  {
        initKoin()
    }
) { App() }