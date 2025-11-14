package com.example.libraryexcersise.data.model

import com.google.gson.annotations.SerializedName

data class GoogleBooksResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<VolumeItem>
)

data class VolumeItem(
    val kind: String,
    val id: String,
    val etag: String,
    val volumeInfo: VolumeInfo,
    val searchInfo: SearchInfo? = null
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val categories: List<String>? = null,
    val imageLinks: ImageLinks? = null,
    val averageRating: Double? = null,
    val ratingsCount: Int? = null
)

data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)

data class SearchInfo(
    val textSnippet: String? = null
)

fun VolumeItem.toBook(): Book {
    return Book(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors ?: emptyList(),
        description = this.volumeInfo.description 
            ?: this.searchInfo?.textSnippet 
            ?: "No description available",
        publishedDate = this.volumeInfo.publishedDate ?: "",
        pageCount = this.volumeInfo.pageCount ?: 0,
        categories = this.volumeInfo.categories ?: emptyList(),
        thumbnailUrl = this.volumeInfo.imageLinks?.thumbnail,
        averageRating = this.volumeInfo.averageRating,
        ratingsCount = this.volumeInfo.ratingsCount,
        publisher = this.volumeInfo.publisher ?: ""
    )
}
