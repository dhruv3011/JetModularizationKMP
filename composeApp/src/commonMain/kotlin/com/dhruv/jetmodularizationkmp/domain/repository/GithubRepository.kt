package com.dhruv.jetmodularizationkmp.domain.repository

import com.dhruv.jetmodularizationkmp.data.mapper.toDomain
import com.dhruv.jetmodularizationkmp.data.remote.GithubApi
import com.dhruv.jetmodularizationkmp.data.remote.model.GithubRepoDto
import com.dhruv.jetmodularizationkmp.data.remote.model.GithubUserDto
import com.dhruv.jetmodularizationkmp.domain.model.GithubRepo
import com.dhruv.jetmodularizationkmp.domain.model.GithubUser
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class GithubRepository(private val api: GithubApi) {
    suspend fun getUserProfile(userName: String): GithubUser {
        val response: HttpResponse = api.getUserProfile(userName)
        return when (response.status) {
            HttpStatusCode.OK -> response.body<GithubUserDto>().toDomain()
            HttpStatusCode.NotFound -> {
                throw Exception("User '$userName' not found on GitHub")
            }
            HttpStatusCode.Forbidden -> {
                throw Exception("GitHub API rate limit reached. Try again later.")
            }
            else -> {
                throw Exception("GitHub Server Error: ${response.status.description}")
            }
        }
    }

    suspend fun getUserRepos(userName: String): List<GithubRepo> {
        val response: HttpResponse = api.getUserRepos(userName)

        return when (response.status) {
            HttpStatusCode.OK -> {
                val dtoList = response.body<List<GithubRepoDto>>()
                dtoList.map { it.toDomain() }
            }
            HttpStatusCode.NotFound -> emptyList() // If user has no repos, or user doesn't exist
            HttpStatusCode.Forbidden -> throw Exception("Rate limit exceeded")
            else -> throw Exception("API Error: ${response.status.value}")
        }
    }
}