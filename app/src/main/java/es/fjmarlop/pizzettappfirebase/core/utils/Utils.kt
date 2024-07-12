package es.fjmarlop.pizzettappfirebase.core.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import javax.inject.Inject

class Utils @Inject constructor(
    private val context: Context,
) {


    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        val passwordLength = password.length >= 6
        val oneNumber = password.any { it.isDigit() }
        val oneUpperCase = password.any { it.isUpperCase() }
        val oneLowerCase = password.any { it.isLowerCase() }
        return passwordLength && oneNumber && oneUpperCase && oneLowerCase
    }


    fun msgToastShort(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun msgToastLong(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }
}