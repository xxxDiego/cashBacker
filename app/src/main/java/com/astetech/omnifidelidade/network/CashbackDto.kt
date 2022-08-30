package com.astetech.omnifidelidade.network

import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.models.Config
import com.google.gson.annotations.SerializedName


data class NetworkCashbackContainer(val videos: List<CashbackNetwork>)

data class CashbackNetwork(
    @field:SerializedName("loja") val loja: String,
    @field:SerializedName("codLoja") val codLoja: String,
    @field:SerializedName("grupo") val grupo: String,
    @field:SerializedName("valorCompra") val valorCompra: Double,
    @field:SerializedName("bonusGerado") val bonusGerado: Double,
    @field:SerializedName("bonusUtilizado") val bonusUtilizado: Double,
    @field:SerializedName("dataCompra") val dataCompra: String,
    @field:SerializedName("validoAte") val validoAte: String,
    @field:SerializedName("vigenteDe") val vigenteDe: String,
    @field:SerializedName("identificadorVenda") val identificadorVenda: String
)


fun NetworkCashbackContainer.asDomainModel(): List<Cashback> {
    return videos.map { networkCashback ->
        Cashback(
            id = 1,
            image = R.drawable.nb,
            empresa = networkCashback.grupo,
            valor = networkCashback.bonusGerado,
            dataValidade =  networkCashback.validoAte ,
            dataAtivacao = networkCashback.validoAte,
            dataCompra = networkCashback.dataCompra ,
            imageUrl = Config.obterImagem(networkCashback.grupo),
            loja = networkCashback.loja,
            valorCompra =  networkCashback.valorCompra,
            valorUtilizado = networkCashback.bonusUtilizado
        )
    }
}