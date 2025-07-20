package com.vharya.tugas7

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vharya.tugas7.database.NotesHelper

class CreateNotesActivity : AppCompatActivity() {
    private lateinit var inputTitle: EditText
    private lateinit var inputDescription: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_create_notes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputTitle = findViewById(R.id.input_title)
        inputDescription = findViewById(R.id.input_description)
        buttonSave = findViewById(R.id.button_save)
        buttonCancel = findViewById(R.id.button_cancel)

        buttonSave.setOnClickListener {
            val title = inputTitle.text.toString()
            val description = inputDescription.text.toString()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(
                    this,
                    "Semua data harus di isi!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            try {
                NotesHelper(this).create(title, description)
                Toast.makeText(
                    this,
                    "Berhasil membuat note baru!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (error: Exception) {
                Toast.makeText(
                    this,
                    "Gagal membuat note!",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("ERROR NOTES", "${error.message}")
            }
        }

        buttonCancel.setOnClickListener {
            finish()
        }
    }
}