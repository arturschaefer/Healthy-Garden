package com.schaefer.healthygarden.ui.garden.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.ui.camera.CameraActivity
import kotlinx.android.synthetic.main.fragment_details_garden.*

class DetailsGardenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details_garden, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        fbAddPicture.setOnClickListener {
            startActivity(Intent(requireContext(), CameraActivity::class.java))
        }
    }
}