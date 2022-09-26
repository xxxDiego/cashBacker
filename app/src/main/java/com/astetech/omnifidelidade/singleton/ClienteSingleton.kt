package com.astetech.omnifidelidade.singleton
import com.astetech.omnifidelidade.extensions.dateToDb
import com.astetech.omnifidelidade.extensions.firstCharUpper
import com.astetech.omnifidelidade.extensions.removeMask
import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.network.ClienteNetwork

object ClienteSingleton {

    var cliente: Cliente = Cliente()



//    var id: String = ""
//    var nome: String = ""
//    var primeiroNome: String = ""
//    var email: String = ""
//    var dataNascimento: String = ""
//    var cadastrado: Boolean = false
//    var cpf: String = ""
//    var celular: String = ""

//    fun factory(cliente: Cliente){
//        this.id = cliente.id
//        this.nome = cliente.nome
//        this.email = cliente.email
//        this.dataNascimento = cliente.dataNascimento
//        this.cadastrado = cliente.cadastrado
//        this.cpf = cliente.cpf
//        this.celular = cliente.celular
//        this.primeiroNome = cliente.nome?.trim()?.split(" ")?.first()?.firstCharUpper()?: ""
//    }
//
//    fun clienteToNetwork(): ClienteNetwork {
//        return ClienteNetwork(
//            nome = this.nome,
//            celular = this.celular.removeMask(),
//            cpf = this.cpf.removeMask(),
//            email = this.email,
//            dataNascimento = this.dataNascimento.dateToDb()
//        )
//    }
}
