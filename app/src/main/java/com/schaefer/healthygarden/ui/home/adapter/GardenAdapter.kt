package com.schaefer.healthygarden.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.schaefer.healthygarden.R
import com.schaefer.healthygarden.domain.model.Garden
import com.schaefer.healthygarden.extensions.inflate

class GardenAdapter (private val arrayList: ArrayList<Garden>): RecyclerView.Adapter<GardenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        val layout = parent.inflate(R.layout.item_home, false)
        return GardenViewHolder(layout)
    }

    override fun onBindViewHolder(holder: GardenViewHolder, position: Int) {
        val garden: Garden = arrayList[position]
        holder.bind(garden)
    }

    override fun getItemCount(): Int = arrayList.size

}