package com.dhruv.jetmodularizationkmp

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, from Shared Code!"
    }
}