package com.astetech.omnifidelidade.ui.atividades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import coil.load
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentAtividadesDetailBinding
import com.astetech.omnifidelidade.databinding.FragmentCashbackDetailBinding
import com.astetech.omnifidelidade.ui.cashback.CashbackDetailFragmentArgs
import com.astetech.omnifidelidade.util.doubleToUi


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


        val imgUri = cashback.imageUrl.toUri().buildUpon().scheme("https").build()
        binding.empresaImage.load(imgUri) {
            placeholder(R.drawable.loading_img)
            error(R.drawable.no_img)
        }

        binding.loja.text = cashback.loja
        binding.marca.text = cashback.empresa
        binding.valorCompraText.text = doubleToUi(cashback.valorCompra)
        binding.valorCashbackText.text = doubleToUi(cashback.valor)
        binding.dataCompraText.text = cashback.dataCompra
        binding.dataAtivacaoText.text = cashback.dataAtivacao
        binding.dataValidadeText.text = cashback.dataValidade
        binding.valorUtilizadoText.text = doubleToUi(cashback.valorUtilizado)
        if (cashback.valorUtilizado > 0) {
            binding.dataUtilizacaoText.text = cashback.dataValidade
        }
        else{
            binding.dataUtilizacaoText.text = "Não utilizado"
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}