package com.example.library.service

import android.content.Context
import com.example.library.dal.JsonDb
import com.example.library.model.Book
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class BookService(private val context: Context) {
    companion object {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        private val listType = Types.newParameterizedType(List::class.java, Book::class.java)
        val adapter: JsonAdapter<List<Book>> = moshi.adapter(listType)
    }

    fun loadBooks(): List<Book> {
        val json = JsonDb.loadJson(context)
        return adapter.fromJson(json) ?: emptyList()
    }

    fun saveBook(book: Book) {
        val books = loadBooks().toMutableList()
        book.id = getNextId()
        books.add(book)
        val json = adapter.toJson(books)
        JsonDb.saveJson(context, json)
    }

    fun updateBook(book: Book) {
        val books = loadBooks().toMutableList()
        val index = books.indexOfFirst { it.id == book.id }
        if (index != -1) {
            books[index].title = book.title
            books[index].author = book.author
            val json = adapter.toJson(books)
            JsonDb.saveJson(context, json)
        }
    }

    fun deleteBook(id: Int) {
        val books = loadBooks().toMutableList()
        val index = books.indexOfFirst { it.id == id }
        if (index != -1) {
            books.removeAt(index)
            val json = adapter.toJson(books)
            JsonDb.saveJson(context, json)
        }
    }

    fun getBook(id: Int): Book? {
        val books = loadBooks()
        return books.find { it.id == id }
    }

    private fun getNextId(): Int {
        val books = loadBooks()
        return books.maxOfOrNull { it.id }?.plus(1) ?: 1
    }
}