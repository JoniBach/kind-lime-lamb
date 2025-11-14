package com.example.libraryexcersise.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val title: String,
    val authors: List<String> = emptyList(),
    val description: String = "",
    val publishedDate: String = "",
    val pageCount: Int = 0,
    val categories: List<String> = emptyList(),
    val thumbnailUrl: String? = null,
    val averageRating: Double? = null,
    val ratingsCount: Int? = null,
    val publisher: String = ""
) : Parcelable
