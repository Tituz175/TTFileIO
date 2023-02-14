package com.example.tt_fileio;

import android.content.Context;
import android.util.Log
import org.json.JSONArray


class NotesData(context: Context) {
    private var mContext: Context? = null
    private val notesData: ArrayList<Note> = ArrayList<Note>()

    init {
        mContext = context
    }

    fun addNote(note: Note) {
        notesData.add(note)
    }

    fun getNoteList(): ArrayList<Note> {
        return notesData
    }

    fun toJSON(): JSONArray {
        val jsonArray = JSONArray()
        for (note in notesData) {
            jsonArray.put(note.toJSON())
        }
        return jsonArray
    }

    fun loadNotes(data: JSONArray) {
        Log.d("NotesData", data.length().toString())
        for (i in 0 until data.length()) {
            val obj = data.getJSONObject(i)
            addNote(
                Note(
                    obj.getString("name"),
                    obj.getString("desc"),
                    obj.getString("date")
                )
            )
        }
    }

    override fun toString(): String {
        var allNotes = ""
        for (note in notesData) {
            allNotes += note.toString()
        }
        return allNotes
    }
}
