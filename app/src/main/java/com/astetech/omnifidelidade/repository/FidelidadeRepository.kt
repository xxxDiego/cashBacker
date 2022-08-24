package com.astetech.omnifidelidade.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.network.ClienteNetwork
import com.astetech.omnifidelidade.network.FidelidadeNetwork
import com.astetech.omnifidelidade.network.FidelidadeService
import com.google.gson.Gson
import java.net.ConnectException


sealed class Resultado<out R> {
    data class Sucesso<out T>(val data: T?) : Resultado<T?>()
    data class Erro(val exception: Exception) : Resultado<Nothing>()
}

class FidelidadeRepository () {

    fun buscaCliente(celular: String) = liveData {
        try {
            val resposta = FidelidadeNetwork.fidelidade.buscaCliente(Config.token, Config.tenant , celular)
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

    fun gravaCliente(cliente: ClienteNetwork) = liveData {

        val gson = Gson()
        var xxxx = gson.toJson(cliente)
        Log.i("catalogo", xxxx)

        try {
            val resposta = FidelidadeNetwork.fidelidade.gravaCliente(Config.token, Config.tenant , cliente)
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