package com.astetech.omnifidelidade.network.response

import com.astetech.omnifidelidade.models.Cliente

data class BuscaClienteResponse(
    val clienteId: String = "",
    var nomeCliente: String,
    var emailCliente: String,
    var dataNascimento: String,
    val cadastrado: Boolean = false,
    var cpf: String,
    var celular: String
){
    fun toClienteDomainModel(): Cliente {
        val cliente = Cliente()
        cliente.id = this.clienteId
        cliente.nome = this.nomeCliente
        cliente.email = this.emailCliente
        cliente.dataNascimento = this.dataNascimento
        cliente.cadastrado = this.cadastrado
        cliente.cpf = this.cpf
        cliente.celular = this.celular
        return cliente
    }
}
