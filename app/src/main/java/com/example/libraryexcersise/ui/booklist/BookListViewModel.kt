package com.example.libraryexcersise.ui.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.libraryexcersise.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BookListViewModel(
    private val repository: BookRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BookListUiState())
    
    val uiState: StateFlow<BookListUiState> = _uiState.asStateFlow()
    
    init {
        loadBooks()
    }
    

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            repository.getBooks()
                .onSuccess { books ->
                    _uiState.update { 
                        it.copy(
                            books = books,
                            filteredBooks = books,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Failed to load books: ${error.message}"
                        )
                    }
                }
        }
    }
    

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        
        if (query.isBlank()) {
            _uiState.update { it.copy(filteredBooks = it.books) }
        } else {
            val filtered = _uiState.value.books.filter { book ->
                book.title.contains(query, ignoreCase = true) ||
                book.authors.any { author -> author.contains(query, ignoreCase = true) }
            }
            _uiState.update { it.copy(filteredBooks = filtered) }
        }
    }

    fun onFilterChanged(filter: FilterOption) {
        _uiState.update { it.copy(selectedFilter = filter) }
        // Todo - actual filtering not implemented as per requirements
    }
    

    fun onSortChanged(sort: SortOption) {
        _uiState.update { it.copy(selectedSort = sort) }
        // Todo - actual sorting not implemented as per requirements
    }
    
    fun clearSearch() {
        _uiState.update { 
            it.copy(
                searchQuery = "",
                filteredBooks = it.books
            )
        }
    }
}

class BookListViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListViewModel::class.java)) {
            return BookListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
