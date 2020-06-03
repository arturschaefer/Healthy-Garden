package com.schaefer.healthygarden.di.modules

import com.schaefer.healthygarden.ui.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel { ProfileViewModel(firebaseAuth = get()) }
}