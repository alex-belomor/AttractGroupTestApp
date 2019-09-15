package com.belomor.attractgrouptestapp.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Long.toStringDate() : String {
    val sdf = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US)
    return try  {
        sdf.format(this * 1000)
    } catch (ex: Exception) {
        "Date parsing failure"
    }
}