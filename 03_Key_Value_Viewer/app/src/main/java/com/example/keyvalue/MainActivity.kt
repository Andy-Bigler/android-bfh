package com.example.keyvalue

import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var grid: GridLayout

    companion object Const {
        const val JSON_STRING = "{'University': 'Bern University of Applied Science', 'Department': 'TI', 'Institute': 'Institute for Cybersecurity and Engineering','Lecture': 'BTI 2016'}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.grid)

        val fields = getFields(JSON_STRING)
        for (key in fields.keys) {
            addRow(key, fields[key])
        }
    }

    private fun getFields(jsonStr: String): HashMap<String, String> {
        val out = HashMap<String, String>()
        try {
            val jo = JSONObject(jsonStr)
            val keys = jo.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                out[key] = jo[key] as String
            }
        } catch (ex: Exception) {
            out["Error"] = ex.toString()
        }
        return out
    }

    private fun addRow(title: String, value: String?) {
        val textViewTitle = TextView(this)
        textViewTitle.text = title
        textViewTitle.textSize = 18f
        textViewTitle.setTypeface(null, Typeface.BOLD)
        textViewTitle.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        grid.addView(textViewTitle, GridLayout.LayoutParams().apply {
            columnSpec = GridLayout.spec(0)
            rowSpec = GridLayout.spec(grid.rowCount)
        })

        // Maybe not the best approach to go with a grid...
        // Wanted to try it out but struggled with the width and word break.
        val textViewValue = TextView(this).apply { width = 700 }
        textViewValue.text = value ?: "-"
        textViewValue.textSize = 16f
        textViewValue.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        grid.addView(textViewValue, GridLayout.LayoutParams().apply {
            columnSpec = GridLayout.spec(1)
            rowSpec = GridLayout.spec(grid.rowCount - 1)
        })
    }
}