package com.schaefer.healthygarden.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.schaefer.healthygarden.R
import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginActivity : AppCompatActivity(){
    private val firebaseAuth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Timber.d(firebaseAuth.currentUser.toString())
    }
}