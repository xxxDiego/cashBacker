package com.astetech.omnifidelidade.models

import com.astetech.omnifidelidade.extensions.dateToDb
import com.astetech.omnifidelidade.extensions.removeMask
import com.astetech.omnifidelidade.network.ClienteNetwork

class Cliente {
    var id: String = ""
    var nome: String = ""
    var email: String = ""
    var dataNascimento: String = ""
    var cadastrado: Boolean = false
    var cpf: String = ""
    var celular: String = ""

    fun clienteToNetwork(): ClienteNetwork {
        return ClienteNetwork(
            nome = this.nome,
            celular = this.celular.removeMask(),
            cpf = this.cpf.removeMask(),
            email = this.email,
            dataNascimento = this.dataNascimento.dateToDb(),
            id = this.id
        )
    }
}