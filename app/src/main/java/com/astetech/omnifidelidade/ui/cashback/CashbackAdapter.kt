package com.astetech.omnifidelidade.ui.cashback

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.models.Cashback

class CashbackAdapter(private val dataset: List<Cashback>) : RecyclerView.Adapter<CashbackAdapter.BonusViewHolder>(){


    class BonusViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView){

        private val empresaTextView: TextView = itemView.findViewById(R.id.empresa_text)
        private val valorTextView: TextView = itemView.findViewById(R.id.valor_text)
        private val dataCompraTextView: TextView = itemView.findViewById(R.id.dataCompra_text)
        private val dataValidadeTextView: TextView = itemView.findViewById(R.id.dataValidade_text)
        private val bonusImageView: ImageView = itemView.findViewById(R.id.bonus_image)

  //      private var currentCashBack: Cashback? = null

//        init {
//            itemView.setOnClickListener {
//                currentCashBack?.let {
//                    onClick(it)
//                }
//            }
//        }

        fun bind(cashBack: Cashback) {
//            currentCashBack = cashBack
//
            empresaTextView.text = cashBack.empresa
            valorTextView.text = cashBack.valor.toString()
            dataCompraTextView.text = cashBack.dataCompra
            dataValidadeTextView.text = cashBack.dataValidade

            if (cashBack.image != null) {
                bonusImageView.setImageResource(cashBack.image)
            } else {
                bonusImageView.setImageResource(R.drawable.nb)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cashback_item, parent, false)
        return BonusViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        val cashBack = dataset[position]
        holder.bind(cashBack)
    }
}