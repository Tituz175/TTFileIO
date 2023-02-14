package com.example.tt_fileio

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var notesData: NotesData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val jsonArray = readDataAsJSON()
        if (jsonArray != null) {
            Log.d("MACT", "data found")
            loadJSONNotes(jsonArray)
        } else {
            Log.d("MACT", "data NOT! found")
            makeData()
        }
    }

    override fun onPause() {
        super.onPause()
        writeDataToFile(notesData)
    }

    private fun makeData() {
        notesData = NotesData(applicationContext)
        for (i in 1..5) {
            val todo = Note("Praise note $i", "Birthday party ", null)
            notesData.addNote(todo)
        }
        writeDataToFile(notesData)
    }

    private fun readDataAsJSON(): JSONArray? {
        try {
            var fileInputStream: FileInputStream? = openFileInput("notes.json")
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                stringBuilder.append(text)
            }
            fileInputStream?.close()
            return JSONArray(stringBuilder.toString())
        } catch (e: FileNotFoundException) {
            return null
        }
    }

    private fun loadJSONNotes(data: JSONArray) {
        val notesData2 = NotesData(applicationContext).apply {
            loadNotes(data)
        }
        Log.d("MainActivity Result", notesData2.toString())
    }


    private fun writeDataToFile(notesData: NotesData) {
        val file: String = "notes.json"
        val data: String = notesData.toJSON().toString()
        val fileOutputStream: FileOutputStream

        try {
            fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}