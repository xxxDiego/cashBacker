package com.astetech.omnifidelidade.network

data class PinNetwork(
    val celular: String,
    val lojaId: String,
    val pin: String? = null
)
