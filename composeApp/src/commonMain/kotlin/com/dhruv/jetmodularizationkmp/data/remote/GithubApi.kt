package com.dhruv.jetmodularizationkmp.data.remote

import com.dhruv.jetmodularizationkmp.data.remote.model.GithubRepoDto
import com.dhruv.jetmodularizationkmp.data.remote.model.GithubUserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class GithubApi(private val client: HttpClient) {
    suspend fun getUserProfile(username: String): HttpResponse {
        return client.get("https://api.github.com/users/$username").body()
    }

    suspend fun getUserRepos(username: String): HttpResponse {
        return client.get("https://api.github.com/users/$username/repos").body()
    }
}
