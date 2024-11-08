package com.example.viewvault.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

object Util {

    fun hasText(editText: TextInputEditText): Boolean{
        var text = editText.text.toString().trim()
        if(text == ""){
            editText.error = "Required"
            return false
        }
        return true
    }

    fun displayCalendar(context: Context, textView: TextView){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
           context,
            {
                    view, year, monthOfYear, dayOfMonth ->
                val dat = ((monthOfYear + 1).toString() + "-" + dayOfMonth + "-" + year)
                textView.setText(dat)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}