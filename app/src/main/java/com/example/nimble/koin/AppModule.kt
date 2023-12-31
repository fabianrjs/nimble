package com.example.nimble.koin

import com.example.nimble.ui.home.HomeViewModel
import com.example.nimble.ui.home.UserViewModel
import com.example.nimble.ui.login.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { UserViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
}
