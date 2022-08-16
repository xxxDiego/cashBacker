package com.astetech.omnifidelidade.ui.cashback

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.astetech.omnifidelidade.models.Cashback


class CashbackAdapter(private val clickListener: CashbackClickListener) :
     ListAdapter<Cashback, CashbackViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashbackViewHolder {
        return CashbackViewHolder.create(parent, clickListener)
    }

    override fun onBindViewHolder(holder: CashbackViewHolder, position: Int) {
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