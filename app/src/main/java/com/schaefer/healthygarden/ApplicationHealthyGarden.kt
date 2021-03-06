package com.schaefer.healthygarden

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.schaefer.healthygarden.di.KoinController
import com.schaefer.healthygarden.di.modules.*
import timber.log.Timber
import timber.log.Timber.DebugTree


class ApplicationHealthyGarden : Application() {
    private val modules = arrayListOf(
        firebaseModule,
        signUpModule,
        loginModule,
        homeModule,
        createEditModule,
        profileModule,
        gardenDetailsModule,
        sharedModule
    )

    override fun onCreate() {
        super.onCreate()

        KoinController.start(this, modules)

        //Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        val settings = FirebaseFirestoreSettings.Builder()
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        FirebaseFirestore.getInstance().firestoreSettings = settings
    }
}