package com.dhruv.jetmodularizationkmp.data.mapper

import com.dhruv.jetmodularizationkmp.data.remote.model.GithubRepoDto
import com.dhruv.jetmodularizationkmp.data.remote.model.GithubUserDto
import com.dhruv.jetmodularizationkmp.domain.model.GithubRepo
import com.dhruv.jetmodularizationkmp.domain.model.GithubUser

fun GithubUserDto.toDomain(): GithubUser {
    return GithubUser(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl,
        htmlUrl = this.htmlUrl,
        name = this.name ?: this.login
    )
}

fun GithubRepoDto.toDomain(): GithubRepo {
    return GithubRepo(
        id = this.id,
        name = this.name,
        description = (this.description ?: "No description available") as String?,
        language = this.language ?: "Unknown",
        stargazersCount = this.stargazersCount,
        forksCount = this.forksCount
    )
}