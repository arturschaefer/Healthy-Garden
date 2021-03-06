package com.schaefer.healthygarden.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageGallery(
    val description: String,
    var url: String,
    val createdAt: String
) : Parcelable