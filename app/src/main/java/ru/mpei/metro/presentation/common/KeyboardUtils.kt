package ru.mpei.metro.presentation.common

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        ContextCompat.getSystemService(this.context, InputMethodManager::class.java)
    inputMethodManager?.showSoftInput(
        this,
        InputMethodManager.SHOW_IMPLICIT
    )
}

fun View.hideKeyboard() {
    val inputMethodManager =
        ContextCompat.getSystemService(this.context, InputMethodManager::class.java)
    inputMethodManager?.hideSoftInputFromWindow(
        this.windowToken,
        InputMethodManager.SHOW_IMPLICIT
    )
}
