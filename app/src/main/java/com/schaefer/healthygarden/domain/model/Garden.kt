package com.schaefer.healthygarden.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Garden(
    val name: String,
    val imageUrl: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val isIndoor: Boolean,
    val listOfGalleryUrl: ArrayList<ImageGallery>,
    val id: String
) : Parcelable
