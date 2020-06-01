package com.schaefer.healthygarden

import android.app.Application
import com.schaefer.healthygarden.di.KoinController
import com.schaefer.healthygarden.di.modules.firebaseModule
import com.schaefer.healthygarden.di.modules.signUpModule
import timber.log.Timber
import timber.log.Timber.DebugTree


class ApplicationHealthyGarden : Application() {
    private val modules = arrayListOf(firebaseModule,
        signUpModule
    )

    override fun onCreate() {
        super.onCreate()

        KoinController.start(this, modules)

        //Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}