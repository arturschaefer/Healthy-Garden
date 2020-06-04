package com.schaefer.healthygarden.ui.create_edit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.ImageGallery
import com.schaefer.healthygarden.extensions.toLiveData
import timber.log.Timber

class CreateEditViewModel(
    private val firebaseStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _name = MutableLiveData<String>()
    private val _description = MutableLiveData<String>()
    private val _date = MutableLiveData<String>()
    private val _isIndoor = MutableLiveData<Boolean>()

    private val _isValidForm = MutableLiveData<Boolean>()
    val isValidForm = _isValidForm.toLiveData()

    private val _isCreatedSuccess = MutableLiveData<Boolean>()
    val isCreatedSuccess = _isCreatedSuccess.toLiveData()

    private val _idToNavigate = MutableLiveData<Int>()
    val idToNavigate = _idToNavigate.toLiveData()

    private val _isEditMode = MutableLiveData<Boolean>()

    fun setName(name: String) {
        _name.value = name
        validateForm()
    }

    fun setDescription(description: String) {
        _description.value = description
        validateForm()
    }

    fun setDate(date: String) {
        _date.value = date
        validateForm()
    }

    fun setIsIndoor(isIndoor: Boolean) {
        _isIndoor.value = isIndoor
        validateForm()
    }

    private fun validateName(): Boolean {
        return _name.value?.isNotEmpty() ?: false
    }

    private fun validateDescription(): Boolean {
        return _description.value?.isNotEmpty() ?: false
    }

    private fun validateDate(): Boolean {
        return _date.value?.isNotEmpty() ?: false
    }

    private fun validateForm() {
        _isValidForm.value = validateName() && validateDescription() && validateDate()
    }

    fun createGarden() {
        val garden = hashMapOf(
            "name" to _name.value,
            "description" to _description.value,
            "createdAt" to _date.value,
            "isIndoor" to _isIndoor.value.toString()
        )
        firebaseAuth.currentUser?.uid?.let {
            firebaseStore.collection(it).add(garden)
                .addOnSuccessListener { documentReference ->
                    _isCreatedSuccess.value = true
                    setNavigation(false)
                    Timber.d(documentReference.toString())
                }.addOnFailureListener { ex ->
                    _isCreatedSuccess.value = false
                    Timber.e(ex)
                }
        }
    }

    private fun setNavigation(isEditMode: Boolean) {
        _idToNavigate.value = when (isEditMode) {
            false -> R.id.action_createEditGardenFragment_to_homeFragment
            true -> R.id.action_createEditGardenFragment_to_detailsGardenFragment
        }
    }

    fun setEditMode(isEditMode: Boolean) {
        _isEditMode.value = isEditMode
    }

    fun editGarden(id: String) {
        val data = hashMapOf(
            "name" to _name.value,
            "description" to _description.value,
            "createdAt" to _date.value,
            "updatedAt" to _date.value,
            "listOfImages" to arrayListOf<ImageGallery>(),
            "isIndoor" to _isIndoor.value.toString()
        )
        firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseStore.collection(uid).document(id).set(data)
                .addOnSuccessListener {
                    setNavigation(true)
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception)
                }
        }
    }

    companion object {
        const val USER_COLLECTION = "users"
    }
}