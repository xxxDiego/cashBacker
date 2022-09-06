package com.astetech.omnifidelidade.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

