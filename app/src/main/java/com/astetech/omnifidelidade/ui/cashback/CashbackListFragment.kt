package com.astetech.omnifidelidade.ui.cashback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.databinding.FragmentCashbackListBinding
import com.astetech.omnifidelidade.models.Cashback
import com.astetech.omnifidelidade.ui.login.LoginViewModel
import com.astetech.omnifidelidade.util.stringToLocalDate
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CashbackListFragment : Fragment() {

    private val cashbackviewModel: CashbackViewModel by activityViewModels()

    private var _binding: FragmentCashbackListBinding? = null
    private val binding get() = _binding!!

    private val cashbackAdapter = CashbackAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )= FragmentCashbackListBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCashbackAdapter()
        initViewListeners()
        cashbackviewModel.refreshDataFromNetwork()
    }

    private fun initViewListeners() {
        cashbackviewModel.cashbackListLive.observe(viewLifecycleOwner, Observer<List<Cashback>> { bonus ->
            if (bonus.isNotEmpty()){
                _binding?.contentImage?.visibility = View.GONE

                var bonusAgrupado = bonus
                    .sortedBy { c -> c.empresa }
                    .groupingBy { it.empresa }
                    .reduce { key, accumulator, element ->
                        accumulator.copy(empresa = key, valor = accumulator.valor + element.valor)
                    }
                    .values.toList()
                cashbackAdapter.submitList(bonusAgrupado)
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