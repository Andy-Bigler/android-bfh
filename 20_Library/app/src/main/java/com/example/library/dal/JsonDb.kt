package com.example.library.dal

import android.content.Context
import java.io.File
import java.io.IOException

object JsonDb {
    fun init(context: Context) {
        try {
            val path = context.filesDir
            val file = File(path, "books.json")
            file.createNewFile() // create file if it doesn't exist
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadJson(context: Context): String {
        val path = context.filesDir
        val file = File(path, "books.json")
        return file.readText()
    }

    fun saveJson(context: Context, json: String) {
        val path = context.filesDir
        val file = File(path, "books.json")
        file.writeText(json)
    }
}