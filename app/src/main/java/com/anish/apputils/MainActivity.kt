package com.anish.apputils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.anish.myutils.utils.CalenderPickerDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("MainActivity", "current time ${CalenderPickerDialog.getCurrentDate()}.")
    }
}