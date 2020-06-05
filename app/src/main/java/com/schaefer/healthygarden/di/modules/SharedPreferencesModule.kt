package com.schaefer.healthygarden.di.modules

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val sharedModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "com.schaefer.healthygarden",
            Context.MODE_PRIVATE
        )
    }
}