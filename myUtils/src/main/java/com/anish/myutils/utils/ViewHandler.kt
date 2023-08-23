package com.anish.myutils.utils

import android.view.View

object ViewHandler {

    private fun showView(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun hideView(view: View) {
        view.visibility = View.GONE
    }

    fun checkView(boolean: Boolean, view: View) {
        if (boolean) {
            showView(view)
        } else {
            hideView(view)
        }
    }
}