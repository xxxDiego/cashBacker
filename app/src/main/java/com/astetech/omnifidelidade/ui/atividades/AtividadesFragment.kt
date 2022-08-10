package com.astetech.omnifidelidade.ui.atividades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astetech.omnifidelidade.R

class AtividadesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_atividades, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val myDataset = Datasource().loadCashBacks()
//
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
//        recyclerView.adapter = CashbackAdapter(myDataset)
//
//        recyclerView.setHasFixedSize(true)
    }
}