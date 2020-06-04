package com.schaefer.healthygarden.ui.garden.details.viewmodel

import android.media.Image
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.domain.model.ImageGallery
import com.schaefer.healthygarden.extensions.toLiveData
import timber.log.Timber

//TODO receber a foto da camera
//TODO fazer upload para o firestorage, com metadaos
//TODO recuperar o link do firestorage
//TODO no sucesso, criar um registro de galeria dentro do jardim
class GardenDetailsViewModel(
    val firebaseAuth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {
    private val _imageWithUrl = MutableLiveData<ImageGallery>()
    val imageWithUrl = _imageWithUrl.toLiveData()

    fun uploadPhoto(imageGallery: ImageGallery, documentReference: String) {
        val uri = Uri.parse(imageGallery.url)
        val metadata = storageMetadata { contentType = "image/jpg" }
        val storageReference = storage.reference.child("gallery/$documentReference/${uri.lastPathSegment}")
        val uploadTask = storageReference.putFile(uri, metadata)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                imageGallery.url = uri.toString()
                _imageWithUrl.value = imageGallery
            }.addOnFailureListener { exception -> Timber.d(exception) }
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("%s - Success", task.toString())
            } else {
                Timber.d("%s - Error", task.toString())
            }
        }.also { it.toString() }
    }

    fun updateGarden(garden: Garden) {
        val data = hashMapOf(
            "name" to garden.name,
            "description" to garden.description,
            "createdAt" to garden.createdAt,
            "createdAt" to garden.updatedAt,
            "isIndoor" to garden.isIndoor.toString(),
            "listOfImages" to garden.listOfImages
        )

        firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseFirestore.collection(uid).document(garden.id).set(data)
                .addOnSuccessListener {
                    Timber.d("Success")
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception)
                }
        }
    }
}