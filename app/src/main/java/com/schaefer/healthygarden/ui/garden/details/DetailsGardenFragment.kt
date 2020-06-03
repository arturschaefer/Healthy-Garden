package com.schaefer.healthygarden.ui.garden.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.domain.model.Mock
import com.schaefer.healthygarden.ui.camera.CameraActivity
import com.schaefer.healthygarden.ui.create_edit.CreateEditGardenActivity
import com.schaefer.healthygarden.ui.garden.details.adapter.GalleryAdapter
import com.schaefer.healthygarden.ui.home.adapter.GardenAdapter
import kotlinx.android.synthetic.main.fragment_details_garden.*
import kotlinx.android.synthetic.main.fragment_home.*

class DetailsGardenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details_garden, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(arguments?.get(ARG_GARDEN) as Garden)
    }

    private fun setupView(garden: Garden) {
        fbAddPicture.setOnClickListener {
            startActivity(Intent(requireContext(), CameraActivity::class.java))
        }
        fbEditGarden.setOnClickListener {view ->
            val bundle = bundleOf(ARG_GARDEN to garden)
            view.findNavController().navigate(R.id.action_detailsGardenFragment_to_createEditGardenFragment, bundle)
//            startActivity(Intent(requireContext(), CreateEditGardenActivity::class.java))
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

        rvGallery.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = GalleryAdapter(Mock.arrayOfGallery)
        }
//        if (garden.listOfGalleryUrl.isEmpty()){
//            tvEmptyGallery.visibility = View.VISIBLE
//        } else {
//        }
    }

    companion object{
        const val ARG_GARDEN = "garden"
    }

}