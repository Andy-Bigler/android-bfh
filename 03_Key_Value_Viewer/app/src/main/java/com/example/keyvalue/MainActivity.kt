package com.example.keyvalue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fields = getFields(jsonString)
    }

    private val jsonString =
        "{'University': 'Bern University of Applied Science', 'Department': 'TI', 'Institute': 'Institute for Cybersecurity and Engineering','Lecture': 'BTI 2016'}"

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
}