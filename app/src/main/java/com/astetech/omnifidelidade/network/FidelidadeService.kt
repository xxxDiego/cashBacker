package com.astetech.omnifidelidade.network

import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.network.response.ClientePostResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FidelidadeService {

    @GET("Relatorio/cliente/detalhe")
     fun buscarCashback(
        @Header("Authorization") authHeader: String?,
        @Header("tenant") tenant: String?,
        @Query("clienteId") clienteId: String,
        @Query("filtroBonus") filtroBonus: String,
        @Query("dataInicio") dataInicio: String,
        @Query("dataFim") dataFim: String,
    ): Call<List<CashbackNetwork>>


     @GET("cliente/{telefone}")
    suspend fun buscaCliente(
        @Header("Authorization") authHeader: String,
        @Header("tenant") tenant: String,
        @Path("telefone") telefone: String
    ): Response<Cliente>


    @POST("cliente")
    suspend fun gravaCliente(
        @Header("Authorization") authHeader: String,
        @Header("tenant") tenant: String,
        @Body cliente: ClienteNetwork
    ): Response<ClientePostResponse>
}

object FidelidadeNetwork {
    private const val BASE_URL = "https://api.omnisige.com.br/omni-fidelidade/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val fidelidade: FidelidadeService = retrofit.create(FidelidadeService::class.java)
}

