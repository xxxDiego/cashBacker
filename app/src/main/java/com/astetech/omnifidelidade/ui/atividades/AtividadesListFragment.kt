package com.astetech.omnifidelidade.ui.atividades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.databinding.FragmentAtividadesListBinding
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.cashback.CashbackAdapter
import com.astetech.omnifidelidade.ui.cashback.CashbackClickListener
import com.astetech.omnifidelidade.ui.cashback.CashbackViewModel
import com.astetech.omnifidelidade.util.CashbackStatus

class AtividadesListFragment :Fragment(), CashbackClickListener {

    private val cashbackviewModel: CashbackViewModel by activityViewModels()

    private var _binding: FragmentAtividadesListBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    private val cashbackAdapter = CashbackAdapter(this)

    private var cashbackStatus = CashbackStatus.TODOS

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
        registerViewListeners()
        fillList()
    }

    private fun fillList() {
        when (cashbackStatus) {
            CashbackStatus.ATIVO -> {
                cashbackviewModel.fitroCashback(CashbackStatus.ATIVO)
            }
            CashbackStatus.PENDENTE -> {
                cashbackviewModel.fitroCashback(CashbackStatus.PENDENTE)
            }
            CashbackStatus.EXPIRADO -> {
                cashbackviewModel.fitroCashback(CashbackStatus.EXPIRADO)
            }
            CashbackStatus.RESGATADO -> {
                cashbackviewModel.fitroCashback(CashbackStatus.RESGATADO)
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

    private fun initCashbackAdapter() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = cashbackAdapter
        }
    }

    private fun registerViewListeners() {

        cashbackviewModel.cashbackListLive.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->

            if (bonus.isNotEmpty()){
                _binding?.contentImage?.visibility = View.GONE
                cashbackAdapter.submitList(bonus)
            }
            else{
                _binding?.contentImage?.visibility = View.VISIBLE
            }
        })

        binding.ativos.setOnClickListener{
            cashbackviewModel.fitroCashback(CashbackStatus.ATIVO)
            cashbackStatus = CashbackStatus.ATIVO
        }
        binding.expirados.setOnClickListener{
            cashbackviewModel.fitroCashback(CashbackStatus.EXPIRADO)
            cashbackStatus = CashbackStatus.EXPIRADO
        }

        binding.resgatados.setOnClickListener{
            cashbackviewModel.fitroCashback(CashbackStatus.RESGATADO)
            cashbackStatus = CashbackStatus.RESGATADO
        }

        binding.pendentes.setOnClickListener{
            cashbackviewModel.fitroCashback(CashbackStatus.PENDENTE)
            cashbackStatus = CashbackStatus.PENDENTE
        }

        binding.todos.setOnClickListener{
            cashbackviewModel.fitroCashback(CashbackStatus.TODOS)
            cashbackStatus = CashbackStatus.TODOS
        }
    }


}