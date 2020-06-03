package com.schaefer.healthygarden.ui.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.schaefer.healthygarden.extensions.toLiveData

class ProfileViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel(){
    private val _hasSignOut = MutableLiveData<Boolean>()
    val hasSignOut = _hasSignOut.toLiveData()

    init {
        firebaseAuth.addAuthStateListener {
            _hasSignOut.value = it.currentUser == null
        }
    }

    fun logout(){
        firebaseAuth.signOut()
    }

    fun getName(): String? {
        return firebaseAuth.currentUser?.displayName
    }

    fun getProfileImage(): Uri? {
        return firebaseAuth.currentUser?.photoUrl
    }

    fun getEmail(): String? {
        return firebaseAuth.currentUser?.email
    }
}