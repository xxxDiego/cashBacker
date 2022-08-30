package com.astetech.omnifidelidade.ui.cashback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.CashbackItemBinding
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.util.obterDataCorrente
import com.astetech.omnifidelidade.util.stringToLocalDate


class CashbackViewHolder(
    cashbackItemBinding: CashbackItemBinding,
    private val clickListener: CashbackClickListener
) : RecyclerView.ViewHolder(cashbackItemBinding.root) {

    private val empresaText = cashbackItemBinding.empresaText
    private val valorText = cashbackItemBinding.valorText
    private val dataAtivacaoText = cashbackItemBinding.dataVigenteDeText
    private val statusText = cashbackItemBinding.statusText
    private val empresaImage = cashbackItemBinding.empresaImage
    private val card = cashbackItemBinding.card

    fun bind(cashback: Cashback) {

        empresaText.text = cashback.empresa
        valorText.text = "R$: " + String.format("%.2f", cashback.valor).replace(".", ",")

        val validade = stringToLocalDate(cashback.dataValidade)
        val ativacao = stringToLocalDate(cashback.dataAtivacao)
        val corrente = obterDataCorrente()

        if (cashback.valorUtilizado > 0){
            dataAtivacaoText.text = ""
            statusText.text = "Resgatado"
            dataAtivacaoText.text = "Resgatado em: " + cashback.dataValidade
        }
        else  if (validade < corrente) {
            dataAtivacaoText.text = ""
            statusText.text = "Expirado"
            dataAtivacaoText.text = "Expirado em: " + cashback.dataValidade

        }
        else{

            if (ativacao > corrente){
                statusText.text = "Pendente"
                dataAtivacaoText.text = "Ativo em: " + cashback.dataAtivacao
            }
            else{
                statusText.text = "Ativo"
                dataAtivacaoText.text = "Válido até " + cashback.dataValidade
            }
        }

        //coil
        val imgUri = cashback.imageUrl.toUri().buildUpon().scheme("https").build()
        empresaImage.load(imgUri) {
            placeholder(R.drawable.loading_img)
            error(R.drawable.no_img)
        }

        card.setOnClickListener {
            clickListener.onClick(cashback)
        }

//            if (cashback.image != null) {
//                empresaImage.setImageResource(R.drawable.nb)
//            } else {
//                empresaImage.setImageResource(R.drawable.nb)
//            }
    }

    companion object {
        fun create(
            parent: ViewGroup, clickListener: CashbackClickListener
        ): CashbackViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = CashbackItemBinding.inflate(inflater, parent, false)
            return CashbackViewHolder(itemBinding, clickListener)
        }
    }
}