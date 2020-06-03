package com.schaefer.healthygarden.di.modules

import com.schaefer.healthygarden.ui.create_edit.viewmodel.CreateEditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createEditModule = module {
    viewModel { CreateEditViewModel(get(), get()) }
}