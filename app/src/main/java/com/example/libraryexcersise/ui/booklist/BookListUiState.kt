package com.example.libraryexcersise.ui.booklist

import com.example.libraryexcersise.data.model.Book

data class BookListUiState(
    val books: List<Book> = emptyList(),
    val filteredBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedFilter: FilterOption = FilterOption.ALL,
    val selectedSort: SortOption = SortOption.TITLE_ASC,
    val error: String? = null
)

enum class FilterOption(val displayName: String) {
    ALL("All Books"),
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    ART("Art"),
    SCIENCE("Science")
}

enum class SortOption(val displayName: String) {
    TITLE_ASC("Title (A-Z)"),
    TITLE_DESC("Title (Z-A)"),
    AUTHOR_ASC("Author (A-Z)"),
    RATING_DESC("Rating (High to Low)"),
    PUBLISHED_DATE_DESC("Newest First")
}
