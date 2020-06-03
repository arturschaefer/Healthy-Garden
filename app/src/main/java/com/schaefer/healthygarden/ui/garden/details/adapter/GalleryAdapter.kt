package com.schaefer.healthygarden.ui.garden.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.domain.model.ImageGallery
import com.schaefer.healthygarden.extensions.inflate
import com.schaefer.healthygarden.ui.home.adapter.GardenViewHolder

class GalleryAdapter (private val arrayList: ArrayList<ImageGallery>): RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val layout = parent.inflate(R.layout.item_gallery, false)
        return GalleryViewHolder(layout)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val garden: ImageGallery = arrayList[position]
        holder.bind(garden)
    }

    override fun getItemCount(): Int = arrayList.size

}