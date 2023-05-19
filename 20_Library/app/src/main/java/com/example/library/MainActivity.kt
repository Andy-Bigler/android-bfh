package com.example.library

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.library.dal.JsonDb
import com.example.library.databinding.ActivityMainBinding
import com.example.library.service.BookService
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var bookService: BookService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JsonDb.init(this)
        bookService = BookService(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_reading, R.id.navigation_read, R.id.navigation_want_to_read
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_export -> {
                requestExportIntent()
                true
            }

            R.id.menu_import -> {
                requestImportIntent()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun requestImportIntent() {
        val pickIntent = Intent(Intent.ACTION_GET_CONTENT)
        pickIntent.type = "application/json"
        startActivityForResult(pickIntent, INTENT_IMPORT_REQUEST)
    }

    private fun requestExportIntent() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/json"
        intent.putExtra(Intent.EXTRA_TITLE, "books.json")

        startActivityForResult(intent, INTENT_EXPORT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            INTENT_IMPORT_REQUEST -> handleIntentImport(data)
            INTENT_EXPORT_REQUEST -> handleIntentExport(data)
        }
    }

    private fun handleIntentImport(intent: Intent?) {
        val importedData: Uri = intent?.data ?: return
        val contentResolver: ContentResolver = applicationContext.contentResolver

        try {
            val jsonString: String? = importedData.let {
                contentResolver.openInputStream(it)?.use { it.bufferedReader().readText() }
            }

            if (jsonString != null && bookService.checkIfJsonIsValid(jsonString)) {
                JsonDb.saveJson(this, jsonString)
                Toast.makeText(this, getString(R.string.toast_imported_successfully), Toast.LENGTH_SHORT).show()
                recreate()
            } else {
                Toast.makeText(this, getString(R.string.toast_invalid_json), Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun handleIntentExport(intent: Intent?) {
        val exportedData: Uri = intent?.data ?: return
        val contentResolver: ContentResolver = applicationContext.contentResolver

        try {
            val jsonString: String = JsonDb.loadJson(this)

            contentResolver.openFileDescriptor(exportedData, "w")?.use {
                FileOutputStream(it.fileDescriptor).use { fos ->
                    fos.write(jsonString.toByteArray())
                    fos.close()
                }
            }
            Toast.makeText(this, getString(R.string.toast_exported_successfully), Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val INTENT_IMPORT_REQUEST = 0
        const val INTENT_EXPORT_REQUEST = 1
    }
}