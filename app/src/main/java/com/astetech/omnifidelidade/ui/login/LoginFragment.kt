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
import com.astetech.omnifidelidade.extensions.navigateWithAnimations
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
    }

    private fun initValidationFields() = mapOf(
        LoginViewModel.INPUT_CELULAR.first to binding.inputLayoutCelular
    )

    private fun listenToAuthenticationStateEvent(validationFields: Map<String, TextInputLayout>) {
        viewModel.authenticationStateEvent.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                is LoginViewModel.AuthenticationState.Authenticated -> {
                    directions = LoginFragmentDirections.actionLoginFragmentToBonus()
                }
                is LoginViewModel.AuthenticationState.InvalidAuthentication -> {
                    authenticationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                    directions = null
                }
                is LoginViewModel.AuthenticationState.NotFound -> {
                   directions = LoginFragmentDirections.actionLoginFragmentToProfileDataFragment(binding.inputCelular.text.toString())
                }
                else -> {
                    directions = null
                }
            }
        })
    }

    private fun registerViewListeners() {

        binding.buttonLoginSignIn.setOnClickListener {
            val usuarioCelular = binding.inputCelular.text.toString()
            viewModel.authenticate(usuarioCelular)

            if (directions != null){
                navController.navigate(directions!!)
            }
        }

        binding.inputCelular.addTextChangedListener(MaskTextWatcher(binding.inputCelular, SimpleMaskFormatter("(NN) NNNNN-NNNN")))

        binding.inputCelular.addTextChangedListener {
            binding.inputLayoutCelular.dismissError()
        }
    }
}
