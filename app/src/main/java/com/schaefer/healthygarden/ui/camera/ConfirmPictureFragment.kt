package com.schaefer.healthygarden.ui.camera

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.ImageGallery
import kotlinx.android.synthetic.main.fragment_confirm_picture.*

class ConfirmPictureFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_confirm_picture, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(arguments?.get(ARG_IMAGE) as String)
    }

    private fun setupView(uriPath: String) {
        btnConfirm.setOnClickListener {
            val imageGallery = ImageGallery(tvImageTitle.text.toString(), uriPath, "")
            val intent = Intent()
            intent.putExtra(IMAGE_GALLERY, imageGallery)
            //TODO see a better way to do with navigation
            requireActivity().setResult(Activity.RESULT_OK, intent)
            requireActivity().finish()
        }

        Glide.with(requireView())
            .load(uriPath)
            .placeholder(R.drawable.placeholder_header_garden)
            .error(R.drawable.placeholder_header_garden)
            .into(ivPhotoTaken)
    }

    companion object {
        const val ARG_IMAGE = "image"
        const val REQUEST_TAKEN_PICTURE = 1000
        const val IMAGE_GALLERY = "image"
    }
}