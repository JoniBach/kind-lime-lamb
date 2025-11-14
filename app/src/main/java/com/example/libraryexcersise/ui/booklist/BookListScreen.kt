package com.example.libraryexcersise.ui.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.libraryexcersise.data.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    uiState: BookListUiState,
    onSearchQueryChanged: (String) -> Unit,
    onFilterChanged: (FilterOption) -> Unit,
    onSortChanged: (SortOption) -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Library") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            SearchBar(
                query = uiState.searchQuery,
                onQueryChanged = onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            
            // Filter and Sort Options (Placeholders)
            FilterSortRow(
                selectedFilter = uiState.selectedFilter,
                selectedSort = uiState.selectedSort,
                onFilterChanged = onFilterChanged,
                onSortChanged = onSortChanged,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Book List
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                uiState.filteredBooks.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No books found")
                    }
                }
                else -> {
                    BookList(
                        books = uiState.filteredBooks,
                        onBookClick = onBookClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier,
        placeholder = { Text("Search books...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
fun FilterSortRow(
    selectedFilter: FilterOption,
    selectedSort: SortOption,
    onFilterChanged: (FilterOption) -> Unit,
    onSortChanged: (SortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Filter Dropdown (Placeholder)
        var filterExpanded by remember { mutableStateOf(false) }
        
        OutlinedButton(
            onClick = { filterExpanded = true },
            modifier = Modifier.weight(1f)
        ) {
            Text("Filter: ${selectedFilter.displayName}")
        }
        
        DropdownMenu(
            expanded = filterExpanded,
            onDismissRequest = { filterExpanded = false }
        ) {
            FilterOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.displayName) },
                    onClick = {
                        onFilterChanged(option)
                        filterExpanded = false
                    }
                )
            }
        }
        
        // Sort Dropdown (Placeholder)
        var sortExpanded by remember { mutableStateOf(false) }
        
        OutlinedButton(
            onClick = { sortExpanded = true },
            modifier = Modifier.weight(1f)
        ) {
            Text("Sort: ${selectedSort.displayName}")
        }
        
        DropdownMenu(
            expanded = sortExpanded,
            onDismissRequest = { sortExpanded = false }
        ) {
            SortOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.displayName) },
                    onClick = {
                        onSortChanged(option)
                        sortExpanded = false
                    }
                )
            }
        }
    }
}

/**
 * Lazy column displaying the list of books.
 */
@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books, key = { it.id }) { book ->
            BookListItem(
                book = book,
                onClick = { onBookClick(book) }
            )
        }
    }
}

/**
 * Individual book item in the list.
 */
@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Book info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (book.authors.isNotEmpty()) {
                    Text(
                        text = book.authors.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                if (book.categories.isNotEmpty()) {
                    Text(
                        text = book.categories.first(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                if (book.averageRating != null) {
                    Text(
                        text = "★ ${book.averageRating}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}
