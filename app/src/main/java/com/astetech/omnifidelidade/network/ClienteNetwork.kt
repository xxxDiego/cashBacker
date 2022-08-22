package com.astetech.omnifidelidade.network

data class ClienteNetwork(
    var nome: String,
    var celular: String,
    var email: String,
    var loja: String = "lojas/110-C",
    var dataNascimento: String,
    var cpf: String
)


