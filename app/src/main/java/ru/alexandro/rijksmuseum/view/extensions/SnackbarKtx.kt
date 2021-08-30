package ru.alexandro.rijksmuseum.view.extensions

import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.alexandro.rijksmuseum.R

fun View.showClosableSnackbar(message: String) {
    makeSnackbar(message, Snackbar.LENGTH_INDEFINITE) {
        setAction(R.string.button_close) { dismiss() }
    }
}

@SuppressLint("ShowToast")
private fun View.makeSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    config: Snackbar.() -> Snackbar = { this }
) = Snackbar.make(this, message, duration)
    .config()
    .show()