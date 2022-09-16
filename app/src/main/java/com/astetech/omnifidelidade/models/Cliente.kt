package com.astetech.omnifidelidade.models

import android.os.Parcelable
import com.astetech.omnifidelidade.extensions.dateToDb
import com.astetech.omnifidelidade.extensions.removeMask
import com.astetech.omnifidelidade.network.ClienteNetwork


@kotlinx.parcelize.Parcelize
data class Cliente (
    val clienteId: String? = null,
    var nomeCliente: String,
    var emailCliente: String,
    var dataNascimento: String,
    val cadastrado: Boolean = false,
    var cpf: String,
    var celular: String
) : Parcelable {

    fun clienteToNetwork(): ClienteNetwork {
        return ClienteNetwork(
            nome = this.nomeCliente,
            celular = this.celular.removeMask(),
            cpf = this.cpf.removeMask(),
            email = this.emailCliente,
            dataNascimento = this.dataNascimento.dateToDb()
        )
    }
    fun NetworkToCliente(clienteNetwork: ClienteNetwork): Cliente {
        return Cliente (
            "",
            nomeCliente = clienteNetwork.nome ,
            celular = clienteNetwork.celular,
            cpf = clienteNetwork.cpf,
            emailCliente = clienteNetwork.email,
            dataNascimento = clienteNetwork.dataNascimento
        )
    }
}