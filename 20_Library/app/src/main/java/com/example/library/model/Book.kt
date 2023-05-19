package com.example.library.model

data class Book(
    var title: String,
    var author: String,
    var status: Status,
    var notes: String = "",
    var owned: Boolean = false,
    var audioBook: Boolean = false,
    var id: Int = 0,
)
