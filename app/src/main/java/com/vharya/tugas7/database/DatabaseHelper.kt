package com.vharya.tugas7.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "db_tugas"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NOTES = "notes"
        private const val NOTES_COLUMN_ID = "id"
        private const val NOTES_COLUMN_TITLE = "title"
        private const val NOTES_COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) return

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS ${UsersHelper.Companion.TABLE} (" +
                    "${UsersHelper.Companion.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${UsersHelper.Companion.COLUMN_USERNAME} TEXT," +
                    "${UsersHelper.Companion.COLUMN_PASSWORD} TEXT" +
                    ")"
        )

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS ${NotesHelper.Companion.TABLE} (" +
                    "${NotesHelper.Companion.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${NotesHelper.Companion.COLUMN_TITLE} TEXT," +
                    "${NotesHelper.Companion.COLUMN_DESCRIPTION} TEXT" +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db == null) return

        db.execSQL("DROP TABLE IF EXISTS ${UsersHelper.Companion.TABLE}")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")

        onCreate(db)
    }
}