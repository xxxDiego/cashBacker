package com.astetech.omnifidelidade.ui.cashback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.CashbackItemBinding
import com.astetech.omnifidelidade.models.Cashback


class CashbackViewHolder(
    cashbackItemBinding: CashbackItemBinding,
    private val clickListener: CashbackClickListener
) : RecyclerView.ViewHolder(cashbackItemBinding.root) {

    private val empresaText = cashbackItemBinding.empresaText
    private val valorText = cashbackItemBinding.valorText
    private val dataCompraText = cashbackItemBinding.dataCompraText
    private val dataValidadeText = cashbackItemBinding.dataValidadeText
    private val empresaImage = cashbackItemBinding.empresaImage
    private val card = cashbackItemBinding.card




    fun bind(cashback: Cashback) {

        empresaText.text = cashback.empresa
        valorText.text = cashback.valor
        dataCompraText.text = cashback.dataCompra
        dataValidadeText.text = cashback.dataValidade

        //coil
        val imgUri = cashback.imageUrl.toUri().buildUpon().scheme("https").build()
        empresaImage.load(imgUri) {
            placeholder(R.drawable.loading_img)
            error(R.drawable.nb)
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