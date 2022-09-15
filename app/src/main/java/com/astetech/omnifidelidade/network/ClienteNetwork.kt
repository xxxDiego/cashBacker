package com.astetech.omnifidelidade.network

import com.astetech.omnifidelidade.models.Config

data class ClienteNetwork(
    var nome: String,
    var celular: String,
    var email: String,
    var loja: String = Config.lojaId,
    var dataNascimento: String,
    var cpf: String
)


