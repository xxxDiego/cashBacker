package com.astetech.omnifidelidade.ui.atividades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.databinding.FragmentAtividadesListBinding
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.cashback.AtividadesAdapter
import com.astetech.omnifidelidade.ui.cashback.CashbackClickListener
import com.astetech.omnifidelidade.ui.cashback.CashbackViewModel
import com.astetech.omnifidelidade.util.CashbackStatus
import com.google.android.material.chip.Chip

class AtividadesListFragment :Fragment(), CashbackClickListener {

    private val cashbackviewModel: CashbackViewModel by activityViewModels()

    private var _binding: FragmentAtividadesListBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    private val atividadesAdapter = AtividadesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )= FragmentAtividadesListBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onClick(cashback: Cashback) {
        var directions = AtividadesListFragmentDirections.actionAtividadesFragmentToAtividadesDetailFragment(cashback)
        navController.navigate(directions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCashbackAdapter()
        initViewListeners()
        initList()

    }

    private fun initCashbackAdapter() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = atividadesAdapter
        }
    }

    private fun initViewListeners() {

        cashbackviewModel.cashbackListLive.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->

            if (bonus.isNotEmpty()){
                _binding?.notFoundAnimation?.visibility = View.GONE
                _binding?.notFoundLabel?.visibility = View.GONE

                atividadesAdapter.submitList(bonus)
            }
            else{
                _binding?.notFoundAnimation?.visibility = View.VISIBLE
                _binding?.notFoundLabel?.visibility = View.VISIBLE
                _binding?.notFoundLabel?.text = mensagem()
                atividadesAdapter.submitList(bonus)
            }
        })

        binding.chipsGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.size > 0){
                group.findViewById<Chip>(checkedIds[0]).text.toString().apply {
                    fillList(this)
                }
            }
        }
    }

    private fun initList() {
        val title = binding.chipsGroup.findViewById<Chip>(binding.chipsGroup.checkedChipId).text.toString()
        fillList(title)
    }

    private fun mensagem(): String{
        when(cashbackviewModel.status){
            CashbackStatus.ATIVOS->{
                return "Você não possui nenhum cashback ativo."
            }
            CashbackStatus.PENDENTES->{
                return "Você não possui nenhum casback pendente."
            }
            CashbackStatus.EXPIRADOS->{
                return "Você não possui nenhum casback expirado."
            }
            CashbackStatus.RESGATADOS->{
                return "Você não possui nenhum casback resgatado."
            }
            CashbackStatus.TODOS->{
                return "Você não possui nenhum cashback."
            }
        }
    }

    private fun fillList(title: String) {
        when (title.uppercase()) {
            CashbackStatus.ATIVOS.name -> {
                cashbackviewModel.fitroCashback(CashbackStatus.ATIVOS)
            }
            CashbackStatus.PENDENTES.name -> {
                cashbackviewModel.fitroCashback(CashbackStatus.PENDENTES)
            }
            CashbackStatus.EXPIRADOS.name -> {
                cashbackviewModel.fitroCashback(CashbackStatus.EXPIRADOS)
            }
            CashbackStatus.RESGATADOS.name -> {
                cashbackviewModel.fitroCashback(CashbackStatus.RESGATADOS)
            }
            else -> {
                if (cashbackviewModel.cashbackList.isNotEmpty()) {
                    cashbackviewModel.fitroCashback(CashbackStatus.TODOS)
                } else {
                    cashbackviewModel.refreshDataFromNetwork()
                }
            }
        }
    }
}