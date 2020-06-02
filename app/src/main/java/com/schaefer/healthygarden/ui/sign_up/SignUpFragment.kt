package com.schaefer.healthygarden.ui.sign_up

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.sign_up.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.ivLogo
import kotlinx.android.synthetic.main.layout_signup.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_signup, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        registerObservers()
        setupTextWatchers()
    }

    private fun setupView() {
        ivLogo.load(resources.getDrawable(R.drawable.ic_logo))

        setupClicks()
    }

    private fun setupClicks() {
        ibBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        includeSignUpForm.btnSignup.setOnClickListener {
            signUpViewModel.authenticateUser(
                includeSignUpForm.etEmail.text.toString().trim(),
                includeSignUpForm.etPassword.text.toString().trim()
            )
        }
    }

    private fun registerObservers() {
        signUpViewModel.nameErrorMessage.observe(viewLifecycleOwner,
            Observer<Int> { messageId -> includeSignUpForm.tilName.error = getString(messageId) })

        signUpViewModel.emailErrorMessage.observe(viewLifecycleOwner,
            Observer<Int> { messageId -> includeSignUpForm.tilEmail.error = getString(messageId) })

        signUpViewModel.passwordErrorMessage.observe(viewLifecycleOwner,
            Observer<Int> { messageId ->
                includeSignUpForm.tilPassword.error = getString(messageId)
            })

        signUpViewModel.isValidForm.observe(viewLifecycleOwner, Observer {
            Timber.d("%s - %s", it.toString(), includeSignUpForm.etPassword.text.toString())
            includeSignUpForm.btnSignup.isEnabled = it
        })

        signUpViewModel.hasSuccessSignUp.observe(viewLifecycleOwner, Observer { success ->
            Timber.d(success.toString())
        })
    }

    private fun setupTextWatchers() {
        lateinit var runnable: Runnable
        includeSignUpForm.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                runnable = Runnable {

                }
                signUpViewModel.setName(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        includeSignUpForm.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                signUpViewModel.setEmail(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        includeSignUpForm.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                signUpViewModel.setPassword(s.toString().trim())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }
}