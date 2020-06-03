package com.schaefer.healthygarden.ui.create_edit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
            "date" to _date.value,
            "isIndoor" to _isIndoor.value.toString()
        )
        firebaseAuth.currentUser?.uid?.let {
            firebaseStore.collection(it).add(garden)
                .addOnSuccessListener { documentReference ->
                    Timber.d(documentReference.toString())
                }.addOnFailureListener { ex ->
                    Timber.e(ex)
                }
        }
    }

    companion object {
        const val USER_COLLECTION = "users"
    }
}