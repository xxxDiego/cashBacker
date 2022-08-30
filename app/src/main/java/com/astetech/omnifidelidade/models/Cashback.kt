package com.astetech.omnifidelidade.models
import android.os.Parcelable



@kotlinx.parcelize.Parcelize
data class Cashback(
    val id: Int,
    val image: Int?,
    val imageUrl: String,
    val empresa: String,
    val loja: String,
    val valor: Double,
    val valorCompra: Double,
    val valorUtilizado: Double,
    val dataValidade: String,
    val dataCompra: String,
    val dataAtivacao: String
) : Parcelable










