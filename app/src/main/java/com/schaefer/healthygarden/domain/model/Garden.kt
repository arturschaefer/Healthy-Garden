package com.schaefer.healthygarden.domain.model

data class Garden(
    val name: String,
    val description: String,
    val date: String,
    val isIndoor: Boolean
)