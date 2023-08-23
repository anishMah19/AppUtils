package com.anish.myutils.utils

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.anish.myutils.listener.SimpleClick

object KeyboardHelper {
    fun onGoAction(editText: EditText, simpleClick: SimpleClick) {
        editText.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(
                p0: TextView?,
                actionId: Int,
                keyEvent: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    simpleClick.onClicked()
                    return true
                }
                return false
            }

        })
    }

    fun onDoneAction(editText: EditText, simpleClick: SimpleClick) {
        editText.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(
                p0: TextView?,
                actionId: Int,
                keyEvent: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    simpleClick.onClicked()
                    return true
                }
                return false
            }

        })
    }
}