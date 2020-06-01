package com.schaefer.healthygarden.di.modules

import com.schaefer.healthygarden.ui.sign_up.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signUpModule = module {
    viewModel { SignUpViewModel(get()) }
}