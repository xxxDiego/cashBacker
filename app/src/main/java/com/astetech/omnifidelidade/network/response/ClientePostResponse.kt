package com.astetech.omnifidelidade.network.response

import com.astetech.omnifidelidade.models.Cliente

data class ClientePostResponse(
    val clienteId: String,
    val nomeCliente: String,
    val emailCliente: String,
    val genero: String,
    val dataNascimento: String,
    val cadastrado: Boolean,
    val cpf: String
){
    fun toClienteDomainModel(): Cliente {
        val cliente = Cliente()
        cliente.id = this.clienteId
        cliente.nome = this.nomeCliente
        cliente.email = this.emailCliente
        cliente.dataNascimento = this.dataNascimento
        cliente.cadastrado = this.cadastrado
        cliente.cpf = this.cpf
        return cliente
    }
}
