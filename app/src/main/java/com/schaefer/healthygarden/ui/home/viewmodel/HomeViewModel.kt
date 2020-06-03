package com.schaefer.healthygarden.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.extensions.toLiveData
import timber.log.Timber

class HomeViewModel(val firebaseFirestore: FirebaseFirestore, val firebaseAuth: FirebaseAuth) :
    ViewModel() {
    private val _listOfGardens = MutableLiveData<ArrayList<Garden>>()
    val listOfGarden = _listOfGardens.toLiveData()

    fun getGardens() {
        firebaseAuth.uid?.let { it ->
            firebaseFirestore.collection(it)
                .get()
                .addOnSuccessListener { result ->
                    result.forEach { document ->
                        Timber.d(document.metadata.toString())
                    }
                }.addOnFailureListener { ex->
                    Timber.e(ex)
                }
        }
    }
}