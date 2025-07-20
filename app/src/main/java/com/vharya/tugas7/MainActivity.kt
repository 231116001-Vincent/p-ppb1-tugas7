package com.vharya.tugas7

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vharya.tugas7.database.UsersHelper

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        if (prefs.getString("username", "") != "") {
            val intent = Intent(this@MainActivity, NotesActivity::class.java)
            startActivity(intent)
        }

        inputUsername = findViewById(R.id.input_username)
        inputPassword = findViewById(R.id.input_password)
        buttonLogin = findViewById(R.id.button_login)
        buttonRegister = findViewById(R.id.button_register)

        buttonLogin.setOnClickListener {
            val username = inputUsername.text.toString()
            val password = inputPassword.text.toString()

            if (UsersHelper(this).login(username, password)) {
                prefs.edit {
                    putString("username", username)
                    apply()
                }

                val intent = Intent(this@MainActivity, NotesActivity::class.java)
                startActivity(intent)
            }
        }

        buttonRegister.setOnClickListener {
            val username = inputUsername.text.toString()
            val password = inputPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Semua data harus di isi!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            try {
                UsersHelper(this).register(username, password)
                Toast.makeText(
                    this,
                    "Berhasil membuat akun!",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (error: Exception) {
                Toast.makeText(
                    this,
                    "Gagal membuat akun!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}