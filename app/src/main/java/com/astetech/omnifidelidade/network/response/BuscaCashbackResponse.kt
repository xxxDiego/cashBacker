package com.astetech.omnifidelidade.network.response

import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.network.CashbackNetwork
import com.astetech.omnifidelidade.network.NetworkCashbackContainer
import com.google.gson.annotations.SerializedName

data class BuscaCashbackResponseContainer(

    val cashbacks: List<BuscaCashbackResponse>
) {
    fun toCashbackDomainModel(): List<Cashback> {
        return cashbacks.map { networkCashback ->
            Cashback(
                id = 1,
                image = R.drawable.nb,
                empresa = networkCashback.grupo,
                valor = networkCashback.bonusGerado,
                dataValidade = networkCashback.validoAte,
                dataAtivacao = networkCashback.vigenteDe,
                dataCompra = networkCashback.dataCompra,
                imageUrl = Config.obterImagem(networkCashback.grupo),
                loja = networkCashback.loja,
                valorCompra = networkCashback.valorCompra,
                valorUtilizado = networkCashback.bonusUtilizado
            )
        }
    }
}

data class BuscaCashbackResponse(
    val loja: String,
    val codLoja: String,
    val grupo: String,
    val valorCompra: Double,
    val bonusGerado: Double,
    val bonusUtilizado: Double,
    val dataCompra: String,
    val validoAte: String,
    val vigenteDe: String,
    val identificadorVenda: String
)
