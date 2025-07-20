package com.vharya.tugas7.database

import android.content.ContentValues
import android.content.Context

class UsersHelper(context: Context) {
    private var databaseHelper = DatabaseHelper(context)

    companion object {
        const val TABLE = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    fun login(username: String, password: String): Boolean {
        val database = databaseHelper.readableDatabase

        val cursor = database.rawQuery(
            "SELECT * FROM $TABLE WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password)
        )

        return cursor.moveToFirst()
    }

    fun register(username: String, password: String) {
        val database = databaseHelper.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)

        try {
            database.insert(TABLE, null, values)
        } catch (error: Exception) {
            throw Exception(error)
        }
    }
}