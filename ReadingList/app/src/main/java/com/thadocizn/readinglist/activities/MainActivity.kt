package com.thadocizn.readinglist.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ScrollView

import com.thadocizn.readinglist.BookController
import com.thadocizn.readinglist.R
import com.thadocizn.readinglist.ViewModel.BookModel
import com.thadocizn.readinglist.classes.Constants

class MainActivity : AppCompatActivity() {

    internal var context: Context
    internal var activity: Activity
    internal var scrollView: ScrollView

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = this.getSharedPreferences(Constants.COM_THADOCIZN_READING_LIST, Context.MODE_PRIVATE)
        context = this
        activity = this
        scrollView = findViewById(R.id.scrollView1)

        findViewById(R.id.btnAddBook).setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                val intent = Intent(context, EditBookActivity::class.java)
                intent.putExtra(Constants.EDIT_BOOK_KEY, String.valueOf(BookModel.getAllBooks().size()))
                startActivityForResult(intent, Constants.EDIT_BOOK_REQUESTCODE)
            }
        })
    }

    @Override
    protected fun onResume() {
        super.onResume()
        scrollView.removeAllViews()
        scrollView.addView(BookController.getBooksView(context, activity))

    }

    @Override
    protected fun onPostResume() {
        super.onPostResume()
    }

    @Override
    protected fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.EDIT_BOOK_REQUESTCODE) {
                BookController.handleEditActivityResult(data)
            }
        }
    }

    companion object {
        var preferences: SharedPreferences
    }
}
