package com.mahmoudashraf.data.sources.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val WORDS_TABLE_NAME = "WORDS_TABLE"
const val WORDS_DB_NAME = "WORDS_BD"
const val DATABASE_VERSION = 1
const val ID_KEY = "id"
const val WORD_KEY = "word"


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, WORDS_DB_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $WORDS_TABLE_NAME($ID_KEY integer primary key AUTOINCREMENT, $WORD_KEY text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $WORDS_TABLE_NAME")
        onCreate(db)
    }

    private fun deleteAllWords() {
        val db = this.writableDatabase
        db.delete(WORDS_TABLE_NAME, null, null)
    }

    fun saveWords(words: List<String>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            deleteAllWords()
            val contentValues = ContentValues()
            for (word in words) {
                contentValues.put(WORD_KEY, word)
                db.insert(WORDS_TABLE_NAME, null, contentValues)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            close()
        }
    }

    fun getWords(): List<String>{
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $WORDS_TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        val wordsList = mutableListOf<String>()
        with(cursor) {
            while (cursor.moveToNext()) {
                val word = getString(getColumnIndexOrThrow(WORD_KEY))
              wordsList.add(word)
            }
        }
        cursor.close()
        close()
        return wordsList
    }
}