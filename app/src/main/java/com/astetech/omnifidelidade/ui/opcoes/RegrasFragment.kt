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
                        "As compras realizadas na loja full price geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma pr??xima compra dentro do prazo m??ximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto m??ximo que poder?? ser aplicado na pr??xima compra ser?? de 50% (cinquenta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerar?? cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 100,00 (cem reais) de cashback, a nova compra precisa ser de no m??nimo R\$ 200,00 (duzentos reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback ser?? igualmente menor proporcionalmente e o valor restante perder?? a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua gera????o ou ser?? acumulado para uso numa pr??xima compra dentro do per??odo de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receber?? o aviso do cashback por SMS, contendo o link dos Termos e Condi????es de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localiza????o da loja dentro do shopping (efeito sombra), ap??s a finaliza????o da compra e o registro do celular no sistema.\n" +
                        "ATEN????O: O desconto ficar?? ativo 07 sete dias ap??s a primeira compra e t??m validade de 30 dias.\n" +
                        "O cashback ?? pessoal e intransfer??vel. Sendo assim, o titular do cashback dever?? estar presencialmente quando da nova compra para que possa ser utilizado o cr??dito.\n"
            }
            "UGG"->{
                return "LOJA FULL PRICE\n" +
                        "As compras realizadas na loja full price geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma pr??xima compra dentro do prazo m??ximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto m??ximo que poder?? ser aplicado na pr??xima compra ser?? de 30% (trinta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerar?? cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 300,00 (trezentos reais) de cashback, a nova compra precisa ser de no m??nimo R\$ 1.000,00 (mil reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback ser?? igualmente menor proporcionalmente e o valor restante perder?? a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua gera????o ou ser?? acumulado para uso numa pr??xima compra dentro do per??odo de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receber?? o aviso do cashback por SMS, contendo o link dos Termos e Condi????es de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localiza????o da loja dentro do shopping (efeito sombra), ap??s a finaliza????o da compra e o registro do celular no sistema.\n"

            }
            "Diesel"->{
               return "LOJAS FULL PRICE E OUTLET\n" +
                        "As compras realizadas na loja full price ou outlet geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma pr??xima compra dentro do prazo m??ximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto m??ximo que poder?? ser aplicado na pr??xima compra ser?? de 50% (cinquenta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerar?? cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 100,00 (cem reais) de cashback, a nova compra precisa ser de no m??nimo R\$ 200,00 (duzentos reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback ser?? igualmente menor proporcionalmente e o valor restante perder?? a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua gera????o ou ser?? acumulado para uso numa pr??xima compra dentro do per??odo de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receber?? o aviso do cashback por SMS, contendo o link dos Termos e Condi????es de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localiza????o da loja dentro do shopping (efeito sombra), ap??s a finaliza????o da compra e o registro do celular no sistema.\n" +
                        "ATEN????O: O desconto ficar?? ativo sete dias ap??s a primeira compra e t??m validade de 30 dias. \n" +
                        "O cashback ?? pessoal e intransfer??vel. Sendo assim, o titular do cashback dever?? estar presencialmente quando da nova compra para que possa ser utilizado o cr??dito.\n"
            }
            "Coach"->{
               return "LOJAS FULL PRICE\n" +
                        "As compras realizadas na loja full price geram um cashback de 10% (dez por cento) independente do valor pago para ser utilizado como desconto em uma pr??xima compra dentro do prazo m??ximo de 30 (trinta) dias.\n" +
                        "COMO FUNCIONA\n" +
                        "O desconto m??ximo que poder?? ser aplicado na pr??xima compra ser?? de 30% (trinta por cento) sobre o valor total da nova compra, lembrando que esta nova compra gerar?? cashback sobre o valor efetivamente pago pelo cliente.\n" +
                        "Exemplo: Com R\$ 300,00 (trezentos reais) de cashback, a nova compra precisa ser de no m??nimo R\$ 1.000,00 (mil reais).\n" +
                        "No caso do valor da nova compra ser menor, o valor do cashback ser?? igualmente menor proporcionalmente e o valor restante perder?? a validade se ultrapassar o prazo de 30 (trinta) dias a contar da data de sua gera????o ou ser?? acumulado para uso numa pr??xima compra dentro do per??odo de sua validade, igualmente de 30 (trinta) dias.\n" +
                        "O cliente receber?? o aviso do cashback por SMS, contendo o link dos Termos e Condi????es de Uso, em torno de 5 (cinco) segundos, podendo variar de acordo com a operadora utilizada pelo cliente e a localiza????o da loja dentro do shopping (efeito sombra), ap??s a finaliza????o da compra e o registro do celular no sistema.\n" +
                        "ATEN????O: O desconto ficar?? ativo um dia ap??s a primeira compra e tem validade de 30 (trinta) dias. \n" +
                        "O cashback ?? pessoal e intransfer??vel. Sendo assim, o titular do cashback dever?? estar presencialmente quando da nova compra para que possa ser utilizado o cr??dito."
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