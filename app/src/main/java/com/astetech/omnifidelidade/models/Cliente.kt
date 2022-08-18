package com.astetech.omnifidelidade.models

data class Cliente (
    val clienteId: String,
    val nomeCliente: String,
    val emailCliente: String,
    val genero: String,
    val dataNascimento: String,
    val cadastrado: Boolean,
    val cpf: String,
    val celular: String
)
