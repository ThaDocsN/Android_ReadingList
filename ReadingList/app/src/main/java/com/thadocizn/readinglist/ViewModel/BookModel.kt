package com.thadocizn.readinglist.ViewModel

import com.thadocizn.readinglist.classes.Book
import com.thadocizn.readinglist.data.SharedPrefsDao

import java.util.ArrayList

object BookModel {

    val allBooks: ArrayList<Book>
        get() = SharedPrefsDao.getAllBooks()

    fun getBook(id: String): String {
        return SharedPrefsDao.getBook(id).toCsvString()
    }

    fun nextId(): String {
        return SharedPrefsDao.getNextId()
    }

    fun updateBook(book: Book) {
        if (book.getId() != null) {
            SharedPrefsDao.updateBook(book)
        }
    }
}
