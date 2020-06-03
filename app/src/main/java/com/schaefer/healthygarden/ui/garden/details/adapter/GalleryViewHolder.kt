package com.schaefer.healthygarden.ui.garden.details.adapter

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.domain.model.ImageGallery
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home.*


class GalleryViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(imageGallery: ImageGallery) {
        Glide.with(containerView)
            .load(imageGallery.url)
            .placeholder(R.drawable.placeholder_header_garden)
            .error(R.drawable.placeholder_header_garden)
            .into(ivGarden)
    }

}