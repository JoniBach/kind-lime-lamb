package com.example.libraryexcersise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.libraryexcersise.data.repository.BookRepository
import com.example.libraryexcersise.navigation.LibraryNavGraph
import com.example.libraryexcersise.ui.theme.LibraryExcersiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryExcersiseTheme {
                // Create repository
                val repository = BookRepository(applicationContext)
                
                // Create navigation controller
                val navController = rememberNavController()
                
                // Display navigation graph
                LibraryNavGraph(
                    navController = navController,
                    repository = repository,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}