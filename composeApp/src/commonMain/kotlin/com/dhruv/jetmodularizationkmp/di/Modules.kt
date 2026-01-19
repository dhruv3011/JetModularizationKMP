package com.dhruv.jetmodularizationkmp.di

import com.dhruv.jetmodularizationkmp.data.remote.GithubApi
import com.dhruv.jetmodularizationkmp.data.remote.createHttpClient
import com.dhruv.jetmodularizationkmp.domain.repository.GithubRepository
import com.dhruv.jetmodularizationkmp.domain.usecase.GetUserProfileUseCase
import com.dhruv.jetmodularizationkmp.domain.usecase.GetUserReposUseCase
import com.dhruv.jetmodularizationkmp.ui.viewmodel.GithubProfileViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single { createHttpClient() }

    single { GithubApi(get()) }

    single<GithubRepository> { GithubRepository(get()) }

    factoryOf(::GetUserProfileUseCase)
    factoryOf(::GetUserReposUseCase)


    viewModelOf(::GithubProfileViewModel)
}