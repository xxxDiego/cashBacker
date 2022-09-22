package com.astetech.omnifidelidade.extensions

import com.astetech.omnifidelidade.models.Config
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun String.removeMask(): String {
    return this
        .replace("(", "")
        .replace(")", "")
        .replace("-", "")
        .replace(" ", "")
        .replace(".", "")
        .replace("/", "")
}

fun String.dateToDb():String{
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
}

fun String.firstCharUpper():String{

   return this.lowercase().replaceFirstChar { firstChar ->
        if (firstChar.isLowerCase()) firstChar.titlecase(
            Locale.getDefault()
        ) else firstChar.toString() }
}

