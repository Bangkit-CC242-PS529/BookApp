package com.example.bookapp.domain

import com.example.bookapp.data.local.BookDao
import com.example.bookapp.data.local.BookEntity
import com.example.bookapp.data.remote.NetworkClient
import com.example.bookapp.data.remote.RecommendRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(private val dao: BookDao) {

    fun getReadingList(): Flow<List<Book>> {
        return dao.getAllBooks().map { list ->
            list.map { Book(it.ISBN, it.authors, it.title) }
        }
    }

    suspend fun saveToReadingList(book: Book) {
        dao.insertBook(
            BookEntity(
                ISBN = book.ISBN,
                authors = book.authors,
                title = book.title
            )
        )
    }

    suspend fun removeFromReadingList(isbn: String) {
        dao.deleteByIsbn(isbn)
    }

    suspend fun countReadingList(): Int {
        return dao.countBooks()
    }

    suspend fun getRecommendations(title: String, k:Int=5): Resource<List<Book>> {
        return try {
            val response = NetworkClient.apiService.getRecommendations(RecommendRequest(title, k))
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.recommendations != null && body.recommendations.isNotEmpty()) {
                    val books = body.recommendations.filter { it.ISBN!=null && it.Authors!=null && it.Titles!=null }
                        .map { Book(it.ISBN!!, it.Authors!!, it.Titles!!) }
                    Resource.Success(books)
                } else {
                    Resource.Error("No recommendations found.")
                }
            } else {
                // Error from server, parse error message if available
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Resource.Error(errorMessage)
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}
