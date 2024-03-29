package com.example.thenews.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewEntity(
    @PrimaryKey val title: String,
    val image: String,
    val description: String,
    val url: String,
    val isFavourite: Boolean = false
)
