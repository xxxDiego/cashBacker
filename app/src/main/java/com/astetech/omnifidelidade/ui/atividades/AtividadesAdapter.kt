package com.astetech.omnifidelidade.ui.cashback

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.atividades.AtividadesViewHolder


class AtividadesAdapter(private val clickListener: CashbackClickListener) :
     ListAdapter<Cashback, AtividadesViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtividadesViewHolder {
        return AtividadesViewHolder.create(parent, clickListener)
    }

    override fun onBindViewHolder(holder: AtividadesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Cashback>() {
            override fun areItemsTheSame(
                oldItem: Cashback,
                newItem: Cashback
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Cashback,
                newItem: Cashback
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface CashbackClickListener {
    fun onClick(cashback: Cashback)
}