package com.astetech.omnifidelidade.network
import com.astetech.omnifidelidade.network.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FidelidadeService {

     @GET("cliente/{telefone}")
    suspend fun buscaCliente(
        @Header("Authorization") authHeader: String,
        @Header("tenant") tenant: String,
        @Path("telefone") telefone: String
    ): Response<BuscaClienteResponse>


    @POST("cliente")
    suspend fun gravaCliente(
        @Header("Authorization") authHeader: String,
        @Header("tenant") tenant: String,
        @Body cliente: ClienteNetwork
    ): Response<ClientePostResponse>

    @PUT("cliente")
    suspend fun alteraCliente(
        @Header("Authorization") authHeader: String,
        @Header("tenant") tenant: String,
        @Body cliente: ClienteNetwork
    ): Response<Boolean>

    @POST("pin/enviar")
    suspend fun enviaPin(
        @Header("Authorization") authHeader: String,
        @Header("tenant") tenant: String,
        @Body pin: PinNetwork
    ): Response<Boolean>

    @POST("pin/validar")
    suspend fun validaPin(
        @Header("Authorization") authHeader: String,
        @Header("tenant") tenant: String,
        @Body pin: PinNetwork
    ): Response<ValidaPinResponse>

    @GET("Relatorio/cliente/detalhe")
    suspend fun buscaCashback(
        @Header("Authorization") authHeader: String?,
        @Header("tenant") tenant: String?,
        @Query("clienteId") clienteId: String,
        @Query("filtroBonus") filtroBonus: String,
        @Query("dataInicio") dataInicio: String,
        @Query("dataFim") dataFim: String,
    ): Response<ArrayList<BuscaCashbackResponse>>

}

object FidelidadeNetwork {
    private const val BASE_URL = "https://api.omnisige.com.br/omni-fidelidade/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val fidelidade: FidelidadeService = retrofit.create(FidelidadeService::class.java)
}

