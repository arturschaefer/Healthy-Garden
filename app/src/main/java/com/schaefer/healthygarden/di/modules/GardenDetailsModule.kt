package com.schaefer.healthygarden.di.modules

import com.schaefer.healthygarden.ui.garden.details.viewmodel.GardenDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gardenDetailsModule = module {
    viewModel {
        GardenDetailsViewModel(
            firebaseAuth = get(),
            firebaseFirestore = get(),
            storage = get()
        )
    }
}