package com.astetech.omnifidelidade.ui.atividades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentAtividadesDetailBinding
import com.astetech.omnifidelidade.databinding.FragmentCashbackDetailBinding
import com.astetech.omnifidelidade.ui.cashback.CashbackDetailFragmentArgs


class AtividadesDetailFragment : Fragment() {
    private var _binding: FragmentAtividadesDetailBinding? = null
    private val binding get() = _binding!!

    private val args: AtividadesDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAtividadesDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        // Receive the arguments in a variable
        val cashback = args.cashback

        binding.name.text = cashback.loja
        binding.description.text = cashback.empresa
        binding.valorCompra.text = "Compra " + cashback.valorCompra
        binding.valorCashback.text = "Cashback " + cashback.valor
        binding.dataCompra.text = "Data " + cashback.dataCompra
        binding.dataValidade.text = "Validade " + cashback.dataValidade
        binding.valorCashbackUtilizado.text = "Utilizado " + cashback.valorUtilizado

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}