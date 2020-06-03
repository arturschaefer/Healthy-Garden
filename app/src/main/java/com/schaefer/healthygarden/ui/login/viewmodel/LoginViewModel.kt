package com.schaefer.healthygarden.ui.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.extensions.toLiveData
import timber.log.Timber


class LoginViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel() {
    private val _isValidForm = MutableLiveData<Boolean>()
    val isValidForm = _isValidForm.toLiveData()

    private val _email = MutableLiveData<String>()
    private val _name = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _emailErrorMessage = MutableLiveData<Int>()
    val emailErrorMessage = _emailErrorMessage.toLiveData()

    private val _passwordErrorMessage = MutableLiveData<Int>()
    val passwordErrorMessage = _passwordErrorMessage.toLiveData()

    private val _messageLoginUser = MutableLiveData<Int>()
    val messageLoginUser = _messageLoginUser.toLiveData()

    private val _hasSuccessLogin = MutableLiveData<Boolean>()
    val hasSuccessLogin = _hasSuccessLogin.toLiveData()

    private val _hasUser = MutableLiveData<Boolean>()
    val hasUser = _hasUser.toLiveData()

    fun signIn(email: String, password: String) {
        Timber.d("signIn:$email")
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithEmail:success")
                    val user = firebaseAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.e("signInWithEmail:failure %s", task.exception)
                }
                if (!task.isSuccessful) {
                    Timber.e(task.exception)
                }
                _hasUser.value = firebaseAuth.currentUser != null
            }
    }

    fun forgotPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Email sent.")
                }else {
                    Timber.e(task.exception)
                }
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

    private fun isValidEmail(email: String?): Boolean {
        return if (email.isNullOrEmpty()) {
            false
        } else {
            email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
        _isValidForm.value = isValidEmail(_email.value) && isValidPassword(_password.value)
    }

    fun isUserAuthenticate(){
        _hasUser.value = firebaseAuth.currentUser != null
    }
}