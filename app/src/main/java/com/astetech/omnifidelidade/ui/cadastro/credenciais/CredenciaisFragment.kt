package com.astetech.omnifidelidade.ui.cadastro.credenciais

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentCredenciaisBinding
import com.astetech.omnifidelidade.extensions.dismissError
import com.astetech.omnifidelidade.extensions.removeMask
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.repository.Resultado
import com.astetech.omnifidelidade.ui.cadastro.CadastroViewModel
import com.google.android.material.textfield.TextInputLayout


class CredenciaisFragment : Fragment() {


    private val cadastroViewModel: CadastroViewModel by activityViewModels()

    private val args: CredenciaisFragmentArgs by navArgs()
    private lateinit var celularArg: String
    private lateinit var telaAnterior: String

    private var _binding: FragmentCredenciaisBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCredenciaisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validationFields = initValidationFields()
        listenToRegistrationStateEvent(validationFields)
        registerViewListeners()
        cancelAuthentication()
        celularArg = args.cliente.celular.removeMask()
        telaAnterior = args.telaAnterior
        enviaPin(celularArg)
    }

    private fun initValidationFields() = mapOf(
        CadastroViewModel.INPUT_PIN.first to binding.inputLayoutPin
    )

    private fun listenToRegistrationStateEvent(validationFields: Map<String, TextInputLayout>) {
        cadastroViewModel.registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
            when (registrationState) {
                is CadastroViewModel.RegistroStatus.CredencialInvalida -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
                is CadastroViewModel.RegistroStatus.RegistroCompleto -> {
                    if (telaAnterior == "ClienteFragment") {
                        gravaCliente()
                    }
                    else {
                        val pref =  activity?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                        with (pref!!.edit()) {
                            putString("telefone", celularArg)
                            apply()
                        }
                        navController.navigate(R.id.action_chooseCredentialsFragment_to_bonus)
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun gravaCliente() {
        cadastroViewModel.gravaCliente().observe(viewLifecycleOwner) {
            var result = it?.let { resultado ->
                when (resultado) {
                    is Resultado.Sucesso -> {
                        resultado.data?.let { cliente ->
                            if (cliente.cadastrado) {
                                Config.clienteId = cliente.clienteId
                            }
                            cliente.cadastrado
                        } ?: false
                    }
                    is Resultado.Erro -> {
                        false
                    }
                }
            } ?: false
            if (result) {
                navController.navigate(R.id.action_chooseCredentialsFragment_to_bonus)
            } else {
                Toast.makeText(activity,"Houve um erro ao cadastrar o cliente!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerViewListeners() {
        binding.buttonChooseCredentialsNext.setOnClickListener {
            val pin = binding.inputPin.text.toString()

            if (cadastroViewModel.createCredentials(pin)) {
                validaPin(pin)
            }
        }
        binding.inputPin.addTextChangedListener {
            binding.inputLayoutPin.dismissError()
        }

        binding.reenviarButton.setOnClickListener {
            enviaPin(celularArg)
        }
    }

    private fun enviaPin(celular: String) {
        cadastroViewModel.enviaPin(celular).observe(viewLifecycleOwner) {
            var mensagem = ""
            val retorno = it?.let { resultado ->
                when (resultado) {
                    is Resultado.Sucesso -> {
                        resultado.data?.let { enviado ->
                            enviado
                        } ?: false
                    }
                    is Resultado.Erro -> {
                        mensagem = resultado.exception?.message.toString()
                        false
                    }
                }
            } ?: false
            if (!retorno) {
                Toast.makeText(activity, mensagem, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validaPin(pin: String) {
        cadastroViewModel.validaPin(pin, celularArg).observe(viewLifecycleOwner) {
            val validado = it?.let { resultado ->
                when (resultado) {
                    is Resultado.Sucesso -> {
                        resultado.data?.let { validaPinResponse ->
                            validaPinResponse.valido
                        } ?: false
                    }
                    is Resultado.Erro -> {
                        false
                    }
                }
            } ?: false
            if (validado) {
                cadastroViewModel.CreateRegistrationCompleted()
            } else {
                binding.inputLayoutPin.error = "PIN inválido"
            }
        }
    }

    private fun cancelAuthentication() {
        cadastroViewModel.refuseAuthentication()
        binding.inputLayoutPin.dismissError()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
