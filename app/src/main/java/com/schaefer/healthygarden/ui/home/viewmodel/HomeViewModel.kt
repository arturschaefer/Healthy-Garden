package com.schaefer.healthygarden.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.extensions.toLiveData
import timber.log.Timber

class HomeViewModel(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _listOfGardens = MutableLiveData<ArrayList<Garden>>()
    val listOfGarden = _listOfGardens.toLiveData()

    fun getGardens() {
        firebaseAuth.uid?.let { it ->
            firebaseFirestore.collection(it)
                .get()
                .addOnSuccessListener { result ->
                    val arrayList = arrayListOf<Garden>()
                    result.forEach { document ->
                        Timber.d(document.metadata.toString())
                        arrayList.add(document.data.toGarden(document.id))
                    }
                    _listOfGardens.value = arrayList
                }.addOnFailureListener { ex ->
                    Timber.e(ex)
                }
        }
    }
}

private fun MutableMap<String, Any>.toGarden(idOfDocument: String): Garden {
    return Garden(
        name = this["name"].toString(),
        imageUrl = this["imageUrl"].toString(),
        updatedAt = this["updatedAt"].toString(),
        createdAt = this["createdAt"].toString(),
        isIndoor = this["isIndoor"].toString().toBoolean(),
        listOfGalleryUrl = arrayListOf(),
        description = this["description"].toString(),
        id = idOfDocument
    )
}
