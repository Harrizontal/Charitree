package com.example.harrisonwjy.charitree


import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    // single instance of HelloRepository - match type HelloRepository
    single { UserRepositoryImpl() }
    //single<UserRepository> { UserRepositoryImpl() }

    // MyViewModel ViewModel
    viewModel { UserViewModel(get()) }
}