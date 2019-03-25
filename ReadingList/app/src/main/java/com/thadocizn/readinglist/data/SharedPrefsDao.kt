package com.thadocizn.readinglist.data

import android.content.SharedPreferences
import android.util.Log

import com.thadocizn.readinglist.activities.MainActivity
import com.thadocizn.readinglist.classes.Book
import com.thadocizn.readinglist.classes.Constants

import java.util.ArrayList
import java.util.Arrays

object SharedPrefsDao {

    private val ids: String?
        get() {
            var keyIds: String? = null
            if (MainActivity.preferences != null) {
                keyIds = MainActivity.preferences.getString(Constants.KEY_IDS, "")
            }
            return keyIds
        }
    private// keys are stored as csv
    val allBookIds: Array<String>
        get() = ids!!.split(",")

    val allBooks: ArrayList<Book>
        get() {
            val ids = allBookIds
            val books = ArrayList(ids.size)
            for (id in ids) {
                if (!id.equals(""))
                    books.add(getBook(id))
            }
            return books
        }

    val nextId: String
        get() {

            val currentId = MainActivity.preferences.getInt(Constants.NEXT_KEY_ID, 0)
            val nextId = currentId + 1
            val editor = MainActivity.preferences.edit()
            editor.putInt(Constants.NEXT_KEY_ID, nextId)
            editor.apply()
            return String.valueOf(nextId)
        }

    fun getBook(id: String): Book? {
        var currentBook: Book? = null
        if (MainActivity.preferences != null) {
            val strBook = MainActivity.preferences.getString(Constants.KEY_ID_PREFIX + id, "")
            currentBook = Book(strBook)
        }
        return currentBook
    }

    fun updateBook(book: Book) {
        if (book.getId().isEmpty()) {
            book.setId(nextId)
        }
        val ids = allBookIds
        var active = false
        for (id in ids) {
            if (book.getId().equals(id)) {
                active = true
                break
            }
        }
        if (!active) {
            addId(book.getId())
        }
        addBook(book)
    }

    private fun addBook(book: Book) {
        val editor = MainActivity.preferences.edit()
        editor.putString(Constants.KEY_ID_PREFIX + book.getId(), book.toCsvString())
        editor.apply()
    }

    private fun addId(id: String) {
        var strGetId = ids
        strGetId = "$strGetId,$id"

        val editor = MainActivity.preferences.edit()
        editor.putString(Constants.KEY_IDS, strGetId.replace(" ", ""))
        editor.apply()
    }
}