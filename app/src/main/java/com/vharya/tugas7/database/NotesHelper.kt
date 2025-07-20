package com.vharya.tugas7.database

import android.content.ContentValues
import android.content.Context
import com.vharya.tugas7.model.NotesModel

class NotesHelper(context: Context) {
    private var databaseHelper = DatabaseHelper(context)

    companion object {
        const val TABLE = "notes"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
    }

    fun getAll(): ArrayList<NotesModel> {
        val database = databaseHelper.readableDatabase
        val list = arrayListOf<NotesModel>()

        val cursor = database.rawQuery(
            "SELECT * FROM $TABLE",
            arrayOf()
        )

        if (cursor.moveToFirst() == false) return list

        with(cursor) {
            list.add(
                NotesModel(
                    getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    getString(getColumnIndexOrThrow(COLUMN_TITLE)),
                    getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                )
            )

            if (moveToNext() == false) return@with
        }

        return list
    }

    fun create(title: String, description: String) {
        val database = databaseHelper.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DESCRIPTION, description)

        try {
            database.insert(TABLE, null, values)
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    fun edit(id: Int, title: String, description: String) {
        val database = databaseHelper.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DESCRIPTION, description)

        try {
            database.update(TABLE, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        } catch (error: Exception) {
            throw Exception(error)
        }
    }

    fun delete(id: Int) {
        val database = databaseHelper.writableDatabase

        try {
            database.delete(TABLE, "$COLUMN_ID = ?", arrayOf(id.toString()))
        } catch (error: Exception) {
            throw Exception(error)
        }
    }
}