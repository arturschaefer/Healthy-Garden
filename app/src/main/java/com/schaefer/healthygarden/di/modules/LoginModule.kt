package com.schaefer.healthygarden.di.modules

import com.schaefer.healthygarden.ui.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
}