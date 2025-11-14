package com.example.libraryexcersise.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.libraryexcersise.data.model.Book
import com.example.libraryexcersise.data.repository.BookRepository
import com.example.libraryexcersise.ui.bookdetails.BookDetailsScreen
import com.example.libraryexcersise.ui.booklist.BookListScreen
import com.example.libraryexcersise.ui.booklist.BookListViewModel
import com.example.libraryexcersise.ui.booklist.BookListViewModelFactory

sealed class Screen(val route: String) {
    data object BookList : Screen("book_list")
    data object BookDetails : Screen("book_details")
}

@Composable
fun LibraryNavGraph(
    navController: NavHostController,
    repository: BookRepository,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.BookList.route,
        modifier = modifier
    ) {
        // Book List Screen
        composable(Screen.BookList.route) {
            val viewModelFactory = BookListViewModelFactory(repository)
            val viewModel: BookListViewModel = viewModel(factory = viewModelFactory)
            val uiState by viewModel.uiState.collectAsState()
            
            BookListScreen(
                uiState = uiState,
                onSearchQueryChanged = viewModel::onSearchQueryChanged,
                onFilterChanged = viewModel::onFilterChanged,
                onSortChanged = viewModel::onSortChanged,
                onBookClick = { book ->
                    // Navigate to details screen with the selected book
                    navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
                    navController.navigate(Screen.BookDetails.route)
                }
            )
        }
        
        // Book Details Screen
        composable(Screen.BookDetails.route) {
            // Retrieve the book from the previous screen's saved state
            val book = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Book>("book")
            
            if (book != null) {
                BookDetailsScreen(
                    book = book,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
