package com.astetech.omnifidelidade.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentLoginBinding
import com.astetech.omnifidelidade.extensions.dismissError
import com.astetech.omnifidelidade.extensions.removeMask
import com.astetech.omnifidelidade.repository.Resultado
import com.astetech.omnifidelidade.singleton.ClienteSingleton
import com.astetech.omnifidelidade.ui.LoadingDialogFragment
import com.astetech.omnifidelidade.ui.LoadingDialogFragment.Companion.hideLoader
import com.astetech.omnifidelidade.ui.LoadingDialogFragment.Companion.showLoader
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

    //Initialize Loader
    private val loadingDialogFragment by lazy { LoadingDialogFragment() }
    private var cellphone = ""


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
        configuracoesIniciais()

    }

    private fun configuracoesIniciais() {
        val pref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        cellphone = pref?.getString("telefone", "") ?: ""
        var checado: Boolean = pref?.getBoolean("modoNoturno",false) == true

        if(checado){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }

        if (!cellphone.isNullOrEmpty()) {
            binding.inputCelular.setText(cellphone)
        }
    }


    private fun initValidationFields() = mapOf(
        LoginViewModel.INPUT_CELULAR.first to binding.inputLayoutCelular
    )

    private fun listenToAuthenticationStateEvent(validationFields: Map<String, TextInputLayout>) {
        viewModel.authenticationStateEvent.observe(
            viewLifecycleOwner
        ) { authenticationState ->
            if (authenticationState is LoginViewModel.AuthenticationState.InvalidAuthentication) {
                authenticationState.fields.forEach { fieldError ->
                    validationFields[fieldError.first]?.error = getString(fieldError.second)
                }
            }
        }
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
        showLoader(loadingDialogFragment,parentFragmentManager)

        viewModel.buscaUsuario(celular).observe(viewLifecycleOwner) {
            val cadastrado = it?.let { resultado ->
                when (resultado) {
                    is Resultado.Sucesso -> {
                        resultado.data?.let { cliente ->
                            if (cliente.cadastrado) {
                                ClienteSingleton.cliente = cliente.toClienteDomainModel()
                                cliente.cadastrado
                            } else {
                                false
                            }
                        } ?: false
                    }
                    is Resultado.Erro -> {
                        false
                    }
                }
            } ?: false

            hideLoader(loadingDialogFragment)

            if (cadastrado){
                if (binding.inputCelular.text.toString().removeMask() == cellphone) {
                    directions = LoginFragmentDirections.actionLoginFragmentToBonus()
                    navController.navigate(directions!!)
                } else {
                    directions =
                        LoginFragmentDirections.actionLoginFragmentToChooseCredentialsFragment("LoginFragment")
                    navController.navigate(directions!!)
                }
            } else {
                directions =
                    LoginFragmentDirections.actionLoginFragmentToProfileDataFragment(binding.inputCelular.text.toString())
                navController.navigate(directions!!)
            }
        }
    }
}
