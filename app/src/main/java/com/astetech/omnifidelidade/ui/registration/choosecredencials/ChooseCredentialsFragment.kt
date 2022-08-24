package com.astetech.omnifidelidade.ui.registration.choosecredencials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentChooseCredentialsBinding
import com.astetech.omnifidelidade.extensions.dismissError
import com.astetech.omnifidelidade.extensions.navigateWithAnimations
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.repository.Resultado
import com.astetech.omnifidelidade.ui.login.LoginFragmentDirections
import com.astetech.omnifidelidade.ui.login.LoginViewModel
import com.astetech.omnifidelidade.ui.registration.RegistrationViewModel
import com.google.android.material.textfield.TextInputLayout


class ChooseCredentialsFragment : Fragment() {


    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    //private val args: ChooseCredentialsFragmentArgs by navArgs()

    private var _binding:  FragmentChooseCredentialsBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseCredentialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validationFields = initValidationFields()
        listenToRegistrationStateEvent(validationFields)
        registerViewListeners()

    }

    private fun initValidationFields() = mapOf(
        RegistrationViewModel.INPUT_PIN.first to binding.inputLayoutPin
    )

    private fun listenToRegistrationStateEvent(validationFields: Map<String, TextInputLayout>) {
        registrationViewModel.registrationStateEvent.observe(viewLifecycleOwner, Observer { registrationState ->
            when (registrationState) {
//                is RegistrationViewModel.RegistrationState.RegistrationCompleted -> {
//                    val token = registrationViewModel.authToken
//                    val username = registrationViewModel.authNome
//
//                    loginViewModel.authenticateToken(token, username)
//                    navController.popBackStack(R.id.bonusFragment, false)
//                }
                is RegistrationViewModel.RegistrationState.InvalidCredentials -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
                else -> {}
            }
        })
    }

    private fun gravaCliente() {
        registrationViewModel.gravaCliente().observe(viewLifecycleOwner){
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
            } else {
                Toast.makeText(activity,"Text!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun registerViewListeners() {
        binding.buttonChooseCredentialsNext.setOnClickListener {
           val pin = binding.inputPin.text.toString()
         if (registrationViewModel.createCredentials(pin)){
             gravaCliente()
         }
        }
        binding.inputPin.addTextChangedListener {
            binding.inputLayoutPin.dismissError()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
