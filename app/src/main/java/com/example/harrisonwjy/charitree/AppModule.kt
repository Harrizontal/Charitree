package com.example.harrisonwjy.charitree


import com.example.harrisonwjy.charitree.repo.TradAuthenticationRepo
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    // single instance of HelloRepository - match type HelloRepository
    single { TradAuthenticationRepo() }
    //single<Repository> { DefaultLogin() }

    // MyViewModel ViewModel
    viewModel { UserViewModel() }
    viewModel { CampaignViewModel() }
}