package com.example.harrisonwjy.charitree


import com.example.harrisonwjy.charitree.repo.CampaignRepo
import com.example.harrisonwjy.charitree.repo.TradAuthenticationRepo
import com.example.harrisonwjy.charitree.viewmodel.CampaignManagerViewModel
import com.example.harrisonwjy.charitree.viewmodel.AuthViewModel
import com.example.harrisonwjy.charitree.viewmodel.CampaignViewModel
import com.example.harrisonwjy.charitree.viewmodel.DonationViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    // single instance of HelloRepository - match type HelloRepository
    single { TradAuthenticationRepo() }
    //single<Repository> { DefaultLogin() }

    // MyViewModel ViewModel
    viewModel { AuthViewModel() }
    viewModel { CampaignManagerViewModel() }
    viewModel { CampaignViewModel() }
    viewModel { DonationViewModel() }
}