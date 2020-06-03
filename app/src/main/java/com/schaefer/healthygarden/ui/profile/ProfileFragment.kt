package com.schaefer.healthygarden.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.login.LoginActivity
import com.schaefer.healthygarden.ui.profile.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        profileViewModel.hasSignOut.observe(viewLifecycleOwner, Observer { hasLogout ->
            if (hasLogout) {
                requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                Timber.e("Error while logout")
            }
        })
    }

    private fun setupView() {
        btnLogout.setOnClickListener {
            profileViewModel.logout()
        }
        tvName.text = profileViewModel.getName()
        tvEmail.text = profileViewModel.getEmail()

        Glide.with(requireView())
            .load(profileViewModel.getProfileImage())
            .placeholder(R.drawable.person_placeholder)
            .error(R.drawable.person_placeholder)
            .into(ivProfileImage)
    }
}
