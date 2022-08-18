package com.astetech.omnifidelidade.extensions

fun String.removeMask(): String {
    return this.replace("(", "")
        .replace(")", "")
        .replace("-", "")
        .replace(" ", "")
}