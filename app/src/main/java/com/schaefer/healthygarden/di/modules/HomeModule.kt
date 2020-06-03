package com.schaefer.healthygarden.di.modules

import com.schaefer.healthygarden.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val homeModule = module {
    //Common
    single { Calendar.getInstance() }

    viewModel { HomeViewModel(get(), get()) }
}