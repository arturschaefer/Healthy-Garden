package com.schaefer.healthygarden.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.GardenDTO
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home.*


class GardenViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(garden: GardenDTO) {
        tvTitle.text = garden.name
        ivGarden.load(garden.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.placeholder_header_garden)
            transformations(CircleCropTransformation())
        }
        cvGardenItem.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_homeFragment_to_detailsGardenFragment)
        }
    }

}