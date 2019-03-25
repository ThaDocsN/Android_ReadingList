package com.thadocizn.readinglist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.thadocizn.readinglist.ViewModel.BookModel
import com.thadocizn.readinglist.activities.EditBookActivity
import com.thadocizn.readinglist.classes.Book
import com.thadocizn.readinglist.classes.Constants

import java.util.ArrayList

object BookController {
    internal var parentLayout: LinearLayout

    fun getBooksView(context: Context, activity: Activity): View {
        val books: ArrayList<Book>
        books = BookModel.getAllBooks()
        parentLayout = LinearLayout(context)
        parentLayout.setOrientation(LinearLayout.VERTICAL)
        for (book in books) {
            getTextView(book, activity, context, parentLayout)
        }
        return parentLayout
    }

    private fun getTextView(book: Book, activity: Activity, context: Context, linearLayout: LinearLayout): TextView {
        val textView = TextView(context)
        textView.setText(book.getTitle())
        textView.setTextSize(24)
        textView.setPadding(10, 10, 10, 10)
        parentLayout.addView(textView)
        textView.setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                val intent = Intent(context, EditBookActivity::class.java)
                intent.putExtra(Constants.CSV_STRING, BookModel.getBook(book.getId()))
                activity.startActivityForResult(intent, Constants.EDIT_BOOK_REQUESTCODE)
            }
        })
        return textView
    }

    fun handleEditActivityResult(intent: Intent) {
        val csvString = intent.getStringExtra(Constants.EDIT_BOOK_KEY)
        val csv = csvString.split(",")
        val title = csv[0]
        val reason = csv[1]
        val id = csv[2]
        val read = Boolean.parseBoolean(csv[3])
        val returnedBook = Book(title, reason, id, read)
        BookModel.updateBook(returnedBook)
    }

}
