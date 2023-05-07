package com.example.contentprovider

import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class CallLogAdapter(context: Context, cursor: Cursor?) :
    CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.call_log_item, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val numberTextView = view?.findViewById<TextView>(R.id.numberTextView)
        val durationTextView = view?.findViewById<TextView>(R.id.durationTextView)
        val typeTextView = view?.findViewById<TextView>(R.id.typeTextView)

        var number: String
        var duration: String
        var type: Int

        try {
            number = cursor?.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER)) ?: "Unknown"
            duration = cursor?.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION)) ?: "0"
            type = cursor?.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE)) ?: -1
        } catch (e: IllegalArgumentException) {
            // Column not found, set default values
            number = "Unknown"
            duration = "0"
            type = -1
        }

        numberTextView?.text = number
        durationTextView?.text = duration
        typeTextView?.text = when (type) {
            CallLog.Calls.OUTGOING_TYPE -> "Outgoing"
            CallLog.Calls.INCOMING_TYPE -> "Incoming"
            CallLog.Calls.MISSED_TYPE -> "Missed"
            else -> "Unknown"
        }
    }
}
