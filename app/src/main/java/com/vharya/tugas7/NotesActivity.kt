package com.vharya.tugas7

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vharya.tugas7.database.NotesHelper

class NotesActivity : AppCompatActivity() {
    private lateinit var listNotes: RecyclerView
    private lateinit var buttonLogout: Button
    private lateinit var buttonNewNote: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val notes = NotesHelper(this).getAll()

        listNotes = findViewById(R.id.list_notes)
        buttonLogout = findViewById(R.id.button_logout)
        buttonNewNote = findViewById(R.id.button_new_note)

        listNotes.layoutManager = LinearLayoutManager(this)
        listNotes.adapter = NotesRecyclerAdapter(this, notes)

        buttonLogout.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
            prefs.edit {
                clear()
            }

            val intent = Intent(this@NotesActivity, MainActivity::class.java)
            startActivity(intent)
        }

        buttonNewNote.setOnClickListener {
            val intent = Intent(this@NotesActivity, CreateNotesActivity::class.java)
            startActivity(intent)
        }
    }
}