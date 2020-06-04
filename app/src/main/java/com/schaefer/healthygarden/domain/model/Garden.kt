package com.schaefer.healthygarden.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Garden(
    var name: String,
    val imageUrl: String,
    var description: String,
    var createdAt: String,
    var updatedAt: String,
    var isIndoor: Boolean,
    val listOfGalleryUrl: ArrayList<ImageGallery>,
    val id: String
) : Parcelable
