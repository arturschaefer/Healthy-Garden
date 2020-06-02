package com.schaefer.healthygarden.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import coil.api.load
import com.google.android.material.textfield.TextInputEditText
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_login.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        ivLogo.load(resources.getDrawable(R.drawable.ic_logo))

        setupClicks()
        registerObservers()
        setupTextWatchers()
    }

    private fun setupClicks() {
        btnSignUp.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        includeLoginForm.btnLogin.setOnClickListener {
            loginViewModel.signIn(
                includeLoginForm.etEmail.text.toString().trim(),
                includeLoginForm.etPassword.text.toString().trim()
            )
        }

        includeLoginForm.tvForgotPassword.setOnClickListener {
            forgotPassword()
        }
    }

    private fun registerObservers() {
        loginViewModel.emailErrorMessage.observe(viewLifecycleOwner,
            Observer<Int> { messageId -> includeLoginForm.tilEmail.error = getString(messageId) })

        loginViewModel.passwordErrorMessage.observe(viewLifecycleOwner,
            Observer<Int> { messageId ->
                includeLoginForm.tilPassword.error = getString(messageId)
            })

        loginViewModel.isValidForm.observe(viewLifecycleOwner, Observer {
            Timber.d("%s - %s", it.toString(), includeLoginForm.etPassword.text.toString())
            includeLoginForm.btnLogin.isEnabled = it
        })

        loginViewModel.hasSuccessSignUp.observe(viewLifecycleOwner, Observer { success ->
            Timber.d(success.toString())
        })
    }

    private fun setupTextWatchers() {
        includeLoginForm.etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.setEmail(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        includeLoginForm.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.setPassword(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    private fun forgotPassword() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.widget_dialog, null)
        dialogBuilder.setView(dialogView)
        val email =
            dialogView.findViewById<View>(R.id.etDialogEmail) as TextInputEditText
        dialogBuilder.setTitle("Forgot the password?")
        dialogBuilder.setMessage("Please confirm your and email. We will send more information to your inbox.")
        dialogBuilder.setPositiveButton(
            "Send"
        ) { _, _ ->
            val emailStr = email.text.toString()
            loginViewModel.forgotPassword(emailStr)
        }
        dialogBuilder.setNegativeButton("Cancel"
        ) { dialog, whichButton ->
            //pass
        }
        val b: AlertDialog = dialogBuilder.create()
        b.show()
    }
}
