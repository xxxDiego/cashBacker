package com.astetech.omnifidelidade.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BonusService {

    @GET("cliente/detalhe")
     fun buscarBonus(
        @Header("Authorization") authHeader: String?,
        @Header("tenant") tenant: String?,
        @Query("clienteId") clienteId: String,
        @Query("filtroBonus") filtroBonus: String,
        @Query("dataInicio") dataInicio: String,
        @Query("dataFim") dataFim: String,
    ): Call<List<NetworkCashback>>
}



object BonusNetwork {
    private const val BASE_URL = "https://api.omnisige.com.br/omni-fidelidade/Relatorio/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val bonus: BonusService = retrofit.create(BonusService::class.java)
}

