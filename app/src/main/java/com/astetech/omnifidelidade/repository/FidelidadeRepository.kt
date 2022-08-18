package com.astetech.omnifidelidade.repository

import androidx.lifecycle.liveData
import com.astetech.omnifidelidade.network.FidelidadeNetwork
import com.astetech.omnifidelidade.network.FidelidadeService
import java.net.ConnectException


sealed class Resultado<out R> {
    data class Sucesso<out T>(val data: T?) : Resultado<T?>()
    data class Erro(val exception: Exception) : Resultado<Nothing>()
}

class FidelidadeRepository () {

    fun buscaCliente(celular: String, token: String, tenant: String) = liveData {
        try {
            val resposta = FidelidadeNetwork.fidelidade.buscaCliente(token, tenant, celular)
            if(resposta.isSuccessful){
                emit(Resultado.Sucesso(data = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao buscar o cliente")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com API")))
        }
        catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }
}