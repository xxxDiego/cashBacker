package com.astetech.omnifidelidade.ui.cashback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.CashbackHomeItemBinding
import com.astetech.omnifidelidade.models.Cashback

class CashbackViewHolder( binding: CashbackHomeItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

    private val empresaImage = binding.empresaHomeImage
    private val empresaText = binding.empresaHomeText
    private val valorText = binding.valorHomeText

    fun bind(cashback: Cashback) {

        val imgUri = cashback.imageUrl.toUri().buildUpon().scheme("https").build()
        empresaImage.load(imgUri) {
            placeholder(R.drawable.loading_img)
            error(R.drawable.no_img)
        }

        empresaText.text = cashback.empresa
        valorText.text = "R$: " + String.format("%.2f", cashback.valor).replace(".", ",")
    }

    companion object {
        fun create(parent: ViewGroup): CashbackViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = CashbackHomeItemBinding.inflate(inflater, parent, false)
            return CashbackViewHolder(itemBinding)
        }
    }
}