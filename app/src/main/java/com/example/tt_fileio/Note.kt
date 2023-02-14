package com.example.tt_fileio

import org.json.JSONObject
import java.util.*


class Note(var name: String, var desc: String, var date: String?) {
    init {
        if (date == null) {
            date = Date().toString()
        }
    }

    fun toJSON():JSONObject {
        val jsonObject = JSONObject().apply {
            put("name", name)
            put("date", date)
            put("desc", desc)
        }
        return jsonObject
    }

    override fun toString(): String {
        return "$name, $date, $desc"
    }
}