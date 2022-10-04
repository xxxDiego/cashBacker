package com.astetech.omnifidelidade.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.network.ClienteNetwork
import com.astetech.omnifidelidade.network.FidelidadeNetwork
import com.astetech.omnifidelidade.network.PinNetwork
import com.astetech.omnifidelidade.singleton.ClienteSingleton
import com.google.gson.Gson
import java.net.ConnectException

sealed class Resultado<out R> {
    data class Sucesso<out T>(val data: T?) : Resultado<T?>()
    data class Erro(val exception: Exception) : Resultado<Nothing>()
}

class FidelidadeRepository() {

    fun buscaCliente(celular: String) = liveData {
        try {
            val resposta =
                FidelidadeNetwork.fidelidade.buscaCliente(Config.token, Config.tenant, celular)
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(data = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao buscar o cliente")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com API")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

    fun gravaCliente(cliente: ClienteNetwork) = liveData {
        try {
            val resposta =
                FidelidadeNetwork.fidelidade.gravaCliente(Config.token, Config.tenant, cliente)
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(data = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao buscar o cliente")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com API")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

    fun alteraCliente(cliente: ClienteNetwork) = liveData {
        try {

            val gson = Gson()
            var xxxx = gson.toJson(cliente)
            Log.i("catalogo", xxxx)

            val resposta =
                FidelidadeNetwork.fidelidade.alteraCliente(Config.token, Config.tenant, cliente)
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(data = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao alterar os dados do cliente")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com API")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

    fun enviaPin(pinNetwork: PinNetwork) = liveData {
        try {
            val resposta =
                FidelidadeNetwork.fidelidade.enviaPin(Config.token, Config.tenant, pinNetwork)
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(data = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao enviar o PIN")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com o serviço")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

    fun validaPin(pinNetwork: PinNetwork) = liveData {
        try {
            val resposta =
                FidelidadeNetwork.fidelidade.validaPin(Config.token, Config.tenant, pinNetwork)
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(data = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao validar o pin")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com API")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

    fun BuscaCashback() = liveData {
        try {
            val filtroBonus = "Todos"
            val dataInicio = "2022-01-01"
            val dataFim = "2022-12-30"

            val resposta = FidelidadeNetwork.fidelidade.buscaCashback(
                Config.token,
                Config.tenant,
                ClienteSingleton.cliente.id,
                filtroBonus,
                dataInicio,
                dataFim
            )
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(data = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao buscar cashbacks!")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com API")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

}