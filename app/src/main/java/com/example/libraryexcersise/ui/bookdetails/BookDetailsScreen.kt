package com.example.libraryexcersise.ui.bookdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.libraryexcersise.data.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    book: Book,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            // Authors
            if (book.authors.isNotEmpty()) {
                DetailSection(
                    label = "Author(s)",
                    content = book.authors.joinToString(", ")
                )
            }
            
            // Publisher and Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (book.publisher.isNotEmpty()) {
                    Column(modifier = Modifier.weight(1f)) {
                        DetailSection(
                            label = "Publisher",
                            content = book.publisher
                        )
                    }
                }
                
                if (book.publishedDate.isNotEmpty()) {
                    Column(modifier = Modifier.weight(1f)) {
                        DetailSection(
                            label = "Published",
                            content = book.publishedDate
                        )
                    }
                }
            }
            
            // Categories
            if (book.categories.isNotEmpty()) {
                DetailSection(
                    label = "Categories",
                    content = book.categories.joinToString(", ")
                )
            }
            
            // Page Count
            if (book.pageCount > 0) {
                DetailSection(
                    label = "Pages",
                    content = "${book.pageCount} pages"
                )
            }
            
            // Rating
            if (book.averageRating != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailSection(
                        label = "Rating",
                        content = "★ ${book.averageRating}"
                    )
                    
                    if (book.ratingsCount != null && book.ratingsCount > 0) {
                        Text(
                            text = "(${book.ratingsCount} ratings)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Description
            if (book.description.isNotEmpty()) {
                Divider()
                
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = book.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DetailSection(
    label: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
