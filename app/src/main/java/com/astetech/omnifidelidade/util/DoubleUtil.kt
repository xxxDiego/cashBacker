package com.astetech.omnifidelidade.util

fun doubleToUi(valor: Double ):String {
    return  "R$: " + String.format("%.2f", valor).replace(".", ",")
}
