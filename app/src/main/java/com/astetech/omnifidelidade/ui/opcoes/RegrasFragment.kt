package com.astetech.omnifidelidade.ui.opcoes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.astetech.omnifidelidade.R


import com.astetech.omnifidelidade.databinding.FragmentRegrasBinding
import com.google.android.material.chip.Chip


class RegrasFragment : Fragment() {

    private var _binding: FragmentRegrasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegrasBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = mutableListOf<String>("New Balance", "UGG", "Diesel", "Coach")
        val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, list)
        binding.regraSpinner.adapter = adapter

        binding.regraSpinner.onItemSelectedListener.apply {


        }



        binding.regraSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.regraText.text = carregarRegra(list[0] )
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.regraText.text = carregarRegra(list[position] )
            }

        }



    }

    private fun carregarRegra(marca: String): String{

        when(marca){
            "New Balance"->{
                return "LOJAS FULL PRICE\n" +
                        "As compras realizadas na loja full price geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma próxima compra dentro do prazo máximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto máximo que poderá ser aplicado na próxima compra será de 50% (cinquenta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerará cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 100,00 (cem reais) de cashback, a nova compra precisa ser de no mínimo R\$ 200,00 (duzentos reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback será igualmente menor proporcionalmente e o valor restante perderá a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua geração ou será acumulado para uso numa próxima compra dentro do período de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receberá o aviso do cashback por SMS, contendo o link dos Termos e Condições de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localização da loja dentro do shopping (efeito sombra), após a finalização da compra e o registro do celular no sistema.\n" +
                        "ATENÇÃO: O desconto ficará ativo 07 sete dias após a primeira compra e têm validade de 30 dias.\n" +
                        "O cashback é pessoal e intransferível. Sendo assim, o titular do cashback deverá estar presencialmente quando da nova compra para que possa ser utilizado o crédito.\n"
            }
            "UGG"->{
                return "LOJA FULL PRICE\n" +
                        "As compras realizadas na loja full price geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma próxima compra dentro do prazo máximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto máximo que poderá ser aplicado na próxima compra será de 30% (trinta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerará cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 300,00 (trezentos reais) de cashback, a nova compra precisa ser de no mínimo R\$ 1.000,00 (mil reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback será igualmente menor proporcionalmente e o valor restante perderá a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua geração ou será acumulado para uso numa próxima compra dentro do período de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receberá o aviso do cashback por SMS, contendo o link dos Termos e Condições de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localização da loja dentro do shopping (efeito sombra), após a finalização da compra e o registro do celular no sistema.\n"

            }
            "Diesel"->{
               return "LOJAS FULL PRICE E OUTLET\n" +
                        "As compras realizadas na loja full price ou outlet geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma próxima compra dentro do prazo máximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto máximo que poderá ser aplicado na próxima compra será de 50% (cinquenta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerará cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 100,00 (cem reais) de cashback, a nova compra precisa ser de no mínimo R\$ 200,00 (duzentos reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback será igualmente menor proporcionalmente e o valor restante perderá a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua geração ou será acumulado para uso numa próxima compra dentro do período de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receberá o aviso do cashback por SMS, contendo o link dos Termos e Condições de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localização da loja dentro do shopping (efeito sombra), após a finalização da compra e o registro do celular no sistema.\n" +
                        "ATENÇÃO: O desconto ficará ativo sete dias após a primeira compra e têm validade de 30 dias. \n" +
                        "O cashback é pessoal e intransferível. Sendo assim, o titular do cashback deverá estar presencialmente quando da nova compra para que possa ser utilizado o crédito.\n"
            }
            "Coach"->{
               return "LOJAS FULL PRICE\n" +
                        "As compras realizadas na loja full price geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma próxima compra dentro do prazo máximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto máximo que poderá ser aplicado na próxima compra será de 30% (trinta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerará cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 300,00 (trezentos reais) de cashback, a nova compra precisa ser de no mínimo R\$ 1.000,00 (mil reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback será igualmente menor proporcionalmente e o valor restante perderá a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua geração ou será acumulado para uso numa próxima compra dentro do período de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receberá o aviso do cashback por SMS, contendo o link dos Termos e Condições de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localização da loja dentro do shopping (efeito sombra), após a finalização da compra e o registro do celular no sistema.\n" +
                        "ATENÇÃO: O desconto ficará ativo um dia após a primeira compra e tem validade de 30 (trinta) dias. \n" +
                        "O cashback é pessoal e intransferível. Sendo assim, o titular do cashback deverá estar presencialmente quando da nova compra para que possa ser utilizado o crédito."
            }
            else ->{
                return ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}