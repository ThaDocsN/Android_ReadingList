package com.thadocizn.readinglist.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText

import com.thadocizn.readinglist.R
import com.thadocizn.readinglist.classes.Book
import com.thadocizn.readinglist.classes.Constants

class EditBookActivity : AppCompatActivity() {
    internal var edBook: EditText
    internal var edReasonToRead: EditText
    internal var checkBox: CheckBox
    internal var strId: String
    internal var strBook: String? = null
    internal var preferences: SharedPreferences

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)
        preferences = this.getSharedPreferences(Constants.COM_THADOCIZN_READING_LIST, Context.MODE_PRIVATE)

        val intent = getIntent()
        strId = intent.getStringExtra(Constants.EDIT_BOOK_KEY)
        strBook = intent.getStringExtra(Constants.CSV_STRING)

        edBook = findViewById(R.id.edBookTitle)
        edReasonToRead = findViewById(R.id.edReasonToRead)
        checkBox = findViewById(R.id.checkboxReadBook)




        findViewById(R.id.btnSubmit).setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                returnData()
            }
        })
        findViewById(R.id.btnCancel).setOnClickListener(object : View.OnClickListener() {
            @Override
            fun onClick(v: View) {
                cancelData()
            }
        })

        if (strBook != null) {
            val book = Book(strBook)
            val name = book.getTitle()
            val reason = book.getReasonToRead()
            strId = book.getId()
            val hasRead = book.isHasBeenRead()

            edBook.setText(name)
            edReasonToRead.setText(reason)
            checkBox.setChecked(hasRead)
        }
    }

    @Override
    fun onBackPressed() {
        super.onBackPressed()
        returnData()
    }

    private fun returnData() {
        val strTitle = edBook.getText().toString()
        val strReasonToRead = edReasonToRead.getText().toString()
        val bool = checkBox.isChecked()

        val strBook = String.format("%s,%s,%s,%b", strTitle, strReasonToRead, strId, bool)

        val resultIntent = Intent()
        resultIntent.putExtra(Constants.EDIT_BOOK_KEY, strBook)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()

    }

    private fun cancelData() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }

}
