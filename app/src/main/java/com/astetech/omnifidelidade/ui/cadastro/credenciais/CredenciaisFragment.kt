package com.astetech.omnifidelidade.ui.cadastro.credenciais

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentCredenciaisBinding
import com.astetech.omnifidelidade.extensions.dismissError
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.repository.Resultado
import com.astetech.omnifidelidade.ui.cadastro.CadastroViewModel
import com.google.android.material.textfield.TextInputLayout


class CredenciaisFragment : Fragment() {


    private val cadastroViewModel: CadastroViewModel by activityViewModels()

    //private val args: ChooseCredentialsFragmentArgs by navArgs()

    private var _binding:  FragmentCredenciaisBinding? = null
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
    }

    private fun initValidationFields() = mapOf(
        CadastroViewModel.INPUT_PIN.first to binding.inputLayoutPin
    )

    private fun listenToRegistrationStateEvent(validationFields: Map<String, TextInputLayout>) {
        cadastroViewModel.registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
            when (registrationState) {
                is CadastroViewModel.RegistrationState.InvalidCredentials -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
                is CadastroViewModel.RegistrationState.RegistrationCompleted -> {
                    gravaCliente()
                }
                else -> {

                }
            }
        }
    }

    private fun gravaCliente() {
        cadastroViewModel.gravaCliente().observe(viewLifecycleOwner){
            val cadastrado = it?.let { resultado ->
                when (resultado) {
                    is Resultado.Sucesso -> {
                        resultado.data?.let { cliente ->
                            if(cliente.cadastrado){
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

            if (cadastrado) {
                navController.navigate(R.id.action_chooseCredentialsFragment_to_bonus)
            }
            else {
                Toast.makeText(activity,"Houve um erro ao cadastrar o cliente!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerViewListeners() {
        binding.buttonChooseCredentialsNext.setOnClickListener {
           val pin = binding.inputPin.text.toString()

            if(cadastroViewModel.createCredentials(pin)){
                validaPin(pin)
            }
        }
        binding.inputPin.addTextChangedListener {
            binding.inputLayoutPin.dismissError()
        }
    }

    private fun validaPin(pin: String){
        cadastroViewModel.validaPin(pin).observe(viewLifecycleOwner){
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
            if (validado){
                cadastroViewModel.CreateRegistrationCompleted()
            }else{
                binding.inputLayoutPin.error = R.string.credenciais_input_layout_error_pin.toString()
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
