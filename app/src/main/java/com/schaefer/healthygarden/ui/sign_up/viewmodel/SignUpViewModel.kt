package com.schaefer.healthygarden.ui.sign_up.viewmodel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.extensions.toLiveData
import timber.log.Timber


class SignUpViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel() {
    private val _isValidForm = MutableLiveData<Boolean>()
    val isValidForm = _isValidForm.toLiveData()

    private val _email = MutableLiveData<String>()
    private val _name = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _emailErrorMessage = MutableLiveData<Int>()
    val emailErrorMessage = _emailErrorMessage.toLiveData()

    private val _nameErrorMessage = MutableLiveData<Int>()
    val nameErrorMessage = _nameErrorMessage.toLiveData()

    private val _passwordErrorMessage = MutableLiveData<Int>()
    val passwordErrorMessage = _passwordErrorMessage.toLiveData()

    private val _messageCreateUser = MutableLiveData<Int>()
    val messageCreateUser = _messageCreateUser.toLiveData()

    private val _hasSuccessSignUp = MutableLiveData<Boolean>()
    val hasSuccessSignUp = _hasSuccessSignUp.toLiveData()

    fun authenticateUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { taskResult ->
                when {
                    taskResult.isSuccessful -> {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            val profileUpdates =
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(_name.value).build()
                            user.updateProfile(profileUpdates).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Timber.d(
                                        "Display name: %s",
                                        FirebaseAuth.getInstance().currentUser?.displayName
                                    )
                                    _messageCreateUser.value = R.string.sign_up_success
                                } else {
                                    Timber.d(task.exception)
                                }
                                _hasSuccessSignUp.value = task.isSuccessful
                            }
                        }
                    }
                    else -> {
                        Timber.d(taskResult.exception)
                        _hasSuccessSignUp.value = false
                        _messageCreateUser.value = R.string.sign_up_error
                    }
                }
            }
    }

    private fun setNameMessageError(isValid: Boolean) {
        _nameErrorMessage.value = when (isValid) {
            true -> R.string.error_name
            else -> R.string.empty_message
        }
    }

    private fun setEmailMessageError(isValid: Boolean) {
        _emailErrorMessage.value = when (isValid) {
            true -> R.string.error_email
            else -> R.string.empty_message
        }
    }

    private fun setPasswordMessageError(isValid: Boolean) {
        _passwordErrorMessage.value = when (isValid) {
            true -> R.string.error_password
            else -> R.string.empty_message
        }
    }

    fun setPassword(password: String) {
        Timber.d(password)
        _password.value = password
        setPasswordMessageError(!isValidPassword(password))
        isValidForm()
    }

    fun setEmail(email: String) {
        _email.value = email
        setEmailMessageError(!isValidEmail(email))
        isValidForm()
    }

    fun setName(name: String) {
        _name.postValue(name)
        setNameMessageError(!isValidName(name))
        isValidForm()
    }

    private fun isValidEmail(email: String?): Boolean {
        return if (email.isNullOrEmpty()) {
            false
        } else {
            email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    private fun isValidName(name: String?): Boolean {
        return if (name.isNullOrEmpty()) {
            false
        } else {
            name.isNotEmpty() && name.split(" ").size > 1
        }
    }

    private fun isValidPassword(password: String?): Boolean {
        return if (password.isNullOrEmpty()) {
            false
        } else {
            password.isNotEmpty() && password.length > 5
        }
    }

    private fun isValidForm() {
        _isValidForm.value =
            isValidName(_name.value) && isValidEmail(_email.value) && isValidPassword(_password.value)
    }
}