package com.astetech.omnifidelidade.models

import androidx.annotation.DrawableRes

import java.util.*

data class Cashback (
    val id: Int,
    @DrawableRes
    val image: Int?,
    val empresa: String,
    val valor: String,
    val dataValidade: String,
    val dataCompra: String
)