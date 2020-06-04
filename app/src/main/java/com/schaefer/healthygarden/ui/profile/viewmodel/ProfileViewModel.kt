package com.schaefer.healthygarden.ui.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.schaefer.healthygarden.extensions.toLiveData
import timber.log.Timber

class ProfileViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel() {
    private val _hasSignOut = MutableLiveData<Boolean>()
    val hasSignOut = _hasSignOut.toLiveData()

    private val _imageProfile = MutableLiveData<String>()
    val imageProfile = _imageProfile.toLiveData()

    init {
        firebaseAuth.addAuthStateListener {
            _hasSignOut.value = it.currentUser == null
        }
    }

    fun logout() {
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

    fun updatePhoto(uriPath: String) {
        val profileUpdate =
            UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(uriPath)).build()

        firebaseAuth.currentUser?.updateProfile(profileUpdate)?.addOnCompleteListener {  task ->
            if (task.isSuccessful){
                Timber.d("Success")
                _imageProfile.value = uriPath
            } else {
                Timber.d("Error")
            }

        }
    }
}