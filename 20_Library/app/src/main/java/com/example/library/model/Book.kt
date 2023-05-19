package com.example.library.model

data class Book(
    var title: String,
    var author: String,
    var status: Status = Status.READING,
    var id: Int = 0,
)
