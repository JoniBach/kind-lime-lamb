package com.example.libraryexcersise.ui.bookdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.libraryexcersise.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailsViewModel(
    private val book: Book
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BookDetailsUiState(book = book))
    
    val uiState: StateFlow<BookDetailsUiState> = _uiState.asStateFlow()
}

data class BookDetailsUiState(
    val book: Book
)

class BookDetailsViewModelFactory(
    private val book: Book
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailsViewModel::class.java)) {
            return BookDetailsViewModel(book) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
