package com.example.mg

import android.util.Base64
import java.io.UnsupportedEncodingException

object Utils {

    @Throws(UnsupportedEncodingException::class)
    fun getBase64String(value: String): String {
        return Base64.encodeToString(value.toByteArray(charset("UTF-8")), Base64.NO_WRAP)
    }
}