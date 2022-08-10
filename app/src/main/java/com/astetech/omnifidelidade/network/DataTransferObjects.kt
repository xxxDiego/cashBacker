package com.astetech.omnifidelidade.network

import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.models.Cashback
import com.google.gson.annotations.SerializedName


data class NetworkCashbackContainer(val videos: List<NetworkCashback>)

data class NetworkCashback(
    @field:SerializedName("loja") val loja :String,
    @field:SerializedName("codLoja") val codLoja: String,
    @field:SerializedName("grupo") val grupo: String,
    @field:SerializedName("valorCompra") val valorCompra: Double,
    @field:SerializedName("bonusGerado") val bonusGerado: Double,
    @field:SerializedName("bonusUtilizado") val bonusUtilizado: Double,
    @field:SerializedName("dataCompra") val dataCompra: String,
    @field:SerializedName("validoAte") val validoAte: String,
    @field:SerializedName("identificadorVenda") val identificadorVenda: String
)

fun NetworkCashbackContainer.asDomainModel(): List<Cashback> {
    return videos.map { networkCashback ->
        Cashback(
            id = 1,
            image = R.drawable.nb,
            empresa = networkCashback.grupo,
            valor = "R$:" + networkCashback.bonusGerado,
            dataValidade = networkCashback.validoAte,
            dataCompra = networkCashback.dataCompra)
    }
}