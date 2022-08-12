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
            valor = "R$: " + String.format("%.2f", networkCashback.bonusGerado).replace(".", ","),
            dataValidade = networkCashback.validoAte,
            dataCompra = networkCashback.dataCompra,
            imageUrl =
            "https://newbalance.vteximg.com.br/assets/vtex.file-manager-graphql/images/new-nb-logo___10afc1c8af26033b6fd063d0a7ec1199.png")
    }
}