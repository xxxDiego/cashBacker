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
import com.astetech.omnifidelidade.ui.cashback.CashbackListFragmentDirections
import com.astetech.omnifidelidade.ui.cashback.CashbackViewModel

class AtividadesListFragment :Fragment(), CashbackClickListener {

    private val cashbackviewModel: CashbackViewModel by activityViewModels()

    private var _binding: FragmentAtividadesListBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    private val cashbackAdapter = CashbackAdapter(this)

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

        cashbackviewModel.refreshDataFromNetwork()

        cashbackviewModel.playlist.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->

            if (bonus.isNotEmpty()){
                _binding?.contentImage?.visibility = View.GONE
                cashbackAdapter.submitList(bonus)
            }
            else{
                _binding?.contentImage?.visibility = View.VISIBLE
            }
        })
    }

    private fun initCashbackAdapter() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = cashbackAdapter
        }
    }


}