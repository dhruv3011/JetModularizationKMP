package com.dhruv.jetmodularizationkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform