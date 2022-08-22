package com.astetech.omnifidelidade.network.response

data class ClientePostResponse(
    val clienteId: String,
    val nomeCliente: String,
    val emailCliente: String,
    val genero: String,
    val dataNascimento: String,
    val cadastrado: Boolean,
    val cpf: String
)
