package com.dhruv.jetmodularizationkmp

import com.dhruv.jetmodularizationkmp.di.sharedModule
import org.koin.core.context.startKoin

private var isKoinStarted = false

fun initKoin() {
    if (isKoinStarted) return
    startKoin {
        modules(sharedModule)
    }
    isKoinStarted = true
}