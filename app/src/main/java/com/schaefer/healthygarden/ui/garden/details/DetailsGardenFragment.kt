package com.schaefer.healthygarden.ui.garden.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.domain.model.ImageGallery
import com.schaefer.healthygarden.domain.model.Mock
import com.schaefer.healthygarden.ui.camera.CameraActivity
import com.schaefer.healthygarden.ui.camera.ConfirmPictureFragment
import com.schaefer.healthygarden.ui.camera.ConfirmPictureFragment.Companion.IMAGE_GALLERY
import com.schaefer.healthygarden.ui.garden.details.adapter.GalleryAdapter
import com.schaefer.healthygarden.ui.garden.details.viewmodel.GardenDetailsViewModel
import kotlinx.android.synthetic.main.fragment_details_garden.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsGardenFragment : Fragment() {
    private val detailsViewModel: GardenDetailsViewModel by viewModel()

    private lateinit var gardenArgument: Garden
    private val galleryAdapter = GalleryAdapter(arrayListOf())

    lateinit var lastImageGallery: ImageGallery

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details_garden, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(arguments?.get(ARG_GARDEN) as Garden)
        setupObservers()
    }

    private fun setupObservers() {
        detailsViewModel.imageWithUrl.observe(viewLifecycleOwner, Observer {imageGallery ->
            galleryAdapter.addImage(imageGallery)
            updateGarden(galleryAdapter.getList())
        })
    }

    private fun updateGarden(arrayList: ArrayList<ImageGallery>) {
        gardenArgument.listOfImages = arrayList
        detailsViewModel.updateGarden(gardenArgument)
    }

    private fun setupView(garden: Garden) {
        gardenArgument = garden
        fbAddPicture.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), CameraActivity::class.java),
                ConfirmPictureFragment.REQUEST_TAKEN_PICTURE
            )
        }
        fbEditGarden.setOnClickListener { view ->
            val bundle = bundleOf(ARG_GARDEN to garden)
            view.findNavController()
                .navigate(R.id.action_detailsGardenFragment_to_createEditGardenFragment, bundle)
        }

        Glide.with(requireView())
            .load(garden.imageUrl)
            .placeholder(R.drawable.placeholder_header_garden)
            .error(R.drawable.placeholder_header_garden)
            .into(ivGardenProfile)

        tvTitle.text = garden.name
        tvDescription.text = garden.description
        tvDate.text = garden.createdAt
        cbIndoor.isChecked = garden.isIndoor


        if (garden.listOfImages.isNullOrEmpty()) {
            tvEmptyGallery.visibility = View.VISIBLE
        } else {
            rvGallery.apply {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = galleryAdapter
                galleryAdapter.swapList(garden.listOfImages)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ConfirmPictureFragment.REQUEST_TAKEN_PICTURE && resultCode == Activity.RESULT_OK) {
            val imageGallery = data?.extras?.get(IMAGE_GALLERY) as ImageGallery
            detailsViewModel.uploadPhoto(imageGallery, gardenArgument.id)
        }
    }

    companion object {
        const val ARG_GARDEN = "garden"
    }

}