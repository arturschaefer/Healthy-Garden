package com.schaefer.healthygarden.ui.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.camera.CameraActivity
import com.schaefer.healthygarden.ui.camera.CameraFragment
import com.schaefer.healthygarden.ui.camera.CameraFragment.Companion.ARG_SIMPLE_PICTURE
import com.schaefer.healthygarden.ui.camera.CameraFragment.Companion.EXTRAS_PICTURE
import com.schaefer.healthygarden.ui.camera.CameraFragment.Companion.REQUEST_SIMPLE_PICTURE
import com.schaefer.healthygarden.ui.login.LoginActivity
import com.schaefer.healthygarden.ui.profile.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private val sharedPreferences: SharedPreferences by inject()

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

        profileViewModel.imageProfile.observe(viewLifecycleOwner, Observer { imageUri ->
            loadImage(imageUri)
        })
    }

    private fun loadImage(imageUri: String?) {
        Glide.with(requireView())
            .load(imageUri)
            .transform(CircleCrop())
            .placeholder(R.drawable.person_placeholder)
            .error(R.drawable.person_placeholder)
            .into(ivProfileImage)
    }

    private fun setupView() {
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        btnLogout.setOnClickListener {
            profileViewModel.logout()
        }
        tvName.text = profileViewModel.getName()
        tvEmail.text = profileViewModel.getEmail()

        loadImage(profileViewModel.getProfileImage().toString())

        ivEditProfile.setOnClickListener {
            sharedPreferences.edit().putString("camera", "profile").apply()
            startActivityForResult(
                Intent(requireContext(), CameraActivity::class.java),
                CameraFragment.REQUEST_SIMPLE_PICTURE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIMPLE_PICTURE && resultCode == Activity.RESULT_OK) {
            Timber.e(data?.extras?.get(EXTRAS_PICTURE).toString())
            profileViewModel.updatePhoto(data?.extras?.get(EXTRAS_PICTURE) as String)
        }
    }

    companion object {
        const val EXTRAS_PICTURE = "picture"
    }
}
