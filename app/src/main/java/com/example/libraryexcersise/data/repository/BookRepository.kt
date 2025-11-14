package com.example.libraryexcersise.data.repository

import android.content.Context
import com.example.libraryexcersise.data.model.Book
import com.example.libraryexcersise.data.model.GoogleBooksResponse
import com.example.libraryexcersise.data.model.toBook
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader


class BookRepository(private val context: Context) {
    
    private val gson = Gson()
    

    suspend fun getBooks(): Result<List<Book>> = withContext(Dispatchers.IO) {
        try {
            // Read the JSON file from assets
            val inputStream = context.assets.open("volumes.json")
            val reader = InputStreamReader(inputStream)
            
            // Parse JSON to GoogleBooksResponse
            val response = gson.fromJson(reader, GoogleBooksResponse::class.java)
            
            // Convert to Book models
            val books = response.items.map { it.toBook() }
            
            reader.close()
            Result.success(books)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchBooks(query: String): Result<List<Book>> = withContext(Dispatchers.IO) {
        try {
            val allBooks = getBooks().getOrThrow()
            
            if (query.isBlank()) {
                Result.success(allBooks)
            } else {
                val filtered = allBooks.filter { book ->
                    book.title.contains(query, ignoreCase = true) ||
                    book.authors.any { it.contains(query, ignoreCase = true) }
                }
                Result.success(filtered)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
