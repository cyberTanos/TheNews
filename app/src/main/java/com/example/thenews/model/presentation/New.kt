package com.example.thenews.model.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class New(
    val title: String,
    val image: String,
    val description: String,
    val url: String,
    val isFavourite: Boolean
) : Parcelable
