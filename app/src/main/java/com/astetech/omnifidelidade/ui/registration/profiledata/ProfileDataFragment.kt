package com.astetech.omnifidelidade.ui.registration.profiledata

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
import com.astetech.omnifidelidade.databinding.FragmentProfileDataBinding
import com.astetech.omnifidelidade.extensions.dismissError
import com.astetech.omnifidelidade.extensions.navigateWithAnimations
import com.astetech.omnifidelidade.ui.registration.RegistrationViewModel
import com.astetech.omnifidelidade.util.Mask
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.textfield.TextInputLayout

class ProfileDataFragment : Fragment() {
    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    private var _binding:  FragmentProfileDataBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    private var directions: NavDirections? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validationFields = initValidationFields()
        listenToRegistrationStateEvent(validationFields)
        registerViewListeners()
    }

    private fun initValidationFields() = mapOf(
        RegistrationViewModel.INPUT_NOME.first to binding.inputLayoutNome,
        RegistrationViewModel.INPUT_CELULAR.first to binding.inputLayoutCelular,
        RegistrationViewModel.INPUT_CPF.first to binding.inputLayoutCpf,
        RegistrationViewModel.INPUT_EMAIL.first to binding.inputLayoutEmail,
        RegistrationViewModel.INPUT_DATANASCIMENTO.first to binding.inputLayoutDataNascimento,
    )

    private fun listenToRegistrationStateEvent(validationFields: Map<String, TextInputLayout>) {
        registrationViewModel.registrationStateEvent.observe(viewLifecycleOwner, Observer { registrationState ->
            when (registrationState) {

                is RegistrationViewModel.RegistrationState.CollectCredentials -> {
                    val name = binding.inputNome.text.toString()
                    directions = ProfileDataFragmentDirections
                        .actionProfileDataFragmentToChooseCredentialsFragment()
                }
                is RegistrationViewModel.RegistrationState.InvalidProfileData -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
                else -> {}
            }
        })
    }

    private fun registerViewListeners() {

        binding.buttonProfileDataNext.setOnClickListener {
            val nome = binding.inputNome.text.toString()
            val celular = binding.inputCelular.text.toString()
            val cpf = binding.inputCpf.text.toString()
            val email = binding.inputEmail.text.toString()
            val dataNascimento = binding.inputDataNascimento.text.toString()
            registrationViewModel.collectProfileData(nome, celular, cpf, email, dataNascimento)

            if (directions != null) {
                navController.navigateWithAnimations(directions!!)
            }
        }

        binding.inputCelular.addTextChangedListener(MaskTextWatcher(binding.inputCelular,
            SimpleMaskFormatter("(NN) NNNNN-NNNN")))

        binding.inputDataNascimento.addTextChangedListener(MaskTextWatcher(binding.inputDataNascimento,
            SimpleMaskFormatter("NN/NN/NNNN")))

        binding.inputCpf.addTextChangedListener(Mask.mask("###.###.###-##", binding.inputCpf))

        binding.inputNome.addTextChangedListener {
            binding.inputLayoutNome.dismissError()
        }
        binding.inputCelular.addTextChangedListener {
            binding.inputLayoutCelular.dismissError()
        }
        binding.inputCpf.addTextChangedListener {
            binding.inputLayoutCpf.dismissError()
        }
        binding.inputEmail.addTextChangedListener {
            binding.inputLayoutEmail.dismissError()
        }
        binding.inputDataNascimento.addTextChangedListener {
            binding.inputLayoutDataNascimento.dismissError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}