package com.astetech.omnifidelidade.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.databinding.FragmentLoginBinding
import com.astetech.omnifidelidade.extensions.dismissError
import com.astetech.omnifidelidade.extensions.removeMask
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.repository.Resultado
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    private var directions: NavDirections? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validationFields = initValidationFields()
        listenToAuthenticationStateEvent(validationFields)
        registerViewListeners()
        cancelAuthentication()
        binding.inputCelular.setText("(11) 94905-2360")
    }

    private fun initValidationFields() = mapOf(
        LoginViewModel.INPUT_CELULAR.first to binding.inputLayoutCelular
    )

    private fun listenToAuthenticationStateEvent(validationFields: Map<String, TextInputLayout>) {
        viewModel.authenticationStateEvent.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->
                if (authenticationState is LoginViewModel.AuthenticationState.InvalidAuthentication) {
                    authenticationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            })
    }

    private fun registerViewListeners() {

        binding.buttonLoginSignIn.setOnClickListener {
            val usuarioCelular = binding.inputCelular.text?.toString()?.removeMask() ?: ""

            if (viewModel.authenticate(usuarioCelular)) {
                buscaCliente(usuarioCelular)
            }
        }

        binding.inputCelular.addTextChangedListener(
            MaskTextWatcher(
                binding.inputCelular,
                SimpleMaskFormatter("(NN) NNNNN-NNNN")
            )
        )

        binding.inputCelular.addTextChangedListener {
            binding.inputLayoutCelular.dismissError()
        }
    }

    private fun cancelAuthentication() {
        viewModel.refuseAuthentication()
        binding.inputLayoutCelular.dismissError()
    }

    private fun buscaCliente(celular: String) {

        viewModel.buscaUsuario(celular).observe(viewLifecycleOwner) {
            val cadastrado = it?.let { resultado ->
                when (resultado) {
                    is Resultado.Sucesso -> {
                        resultado.data?.let { cliente ->
                            if(cliente.cadastrado){
                                Config.clienteId = cliente.clienteId.toString()
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
                directions = LoginFragmentDirections.actionLoginFragmentToBonus()
                navController.navigate(directions!!)
            } else {
                directions =
                    LoginFragmentDirections.actionLoginFragmentToProfileDataFragment(binding.inputCelular.text.toString())
                navController.navigate(directions!!)
            }
        }
    }
}
