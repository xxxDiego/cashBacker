package com.astetech.omnifidelidade.ui.cadastro.cliente

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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.FragmentClienteBinding
import com.astetech.omnifidelidade.extensions.dismissError
import com.astetech.omnifidelidade.extensions.navigateWithAnimations
import com.astetech.omnifidelidade.extensions.removeMask
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.repository.Resultado
import com.astetech.omnifidelidade.singleton.ClienteSingleton
import com.astetech.omnifidelidade.ui.cadastro.CadastroViewModel
import com.astetech.omnifidelidade.ui.login.LoginFragmentDirections
import com.astetech.omnifidelidade.ui.opcoes.OpcoesFragmentDirections
import com.astetech.omnifidelidade.util.Mask
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.textfield.TextInputLayout

class ClienteFragment : Fragment() {
    private val cadastroViewModel: CadastroViewModel by activityViewModels()

    private val args: ClienteFragmentArgs by navArgs()

    private var _binding:  FragmentClienteBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    private var directions: NavDirections? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validationFields = initValidationFields()
        listenToRegistrationStateEvent(validationFields)
        registerViewListeners()
        cancelAuthentication()
        initInfos()
    }

    private fun initInfos(){
        if(ClienteSingleton.cliente.cadastrado){
            binding.inputCelular.setText(ClienteSingleton.cliente.celular)
            binding.inputNome.setText(ClienteSingleton.cliente.nome)
            binding.inputCpf.setText(ClienteSingleton.cliente.cpf)
            binding.inputEmail.setText(ClienteSingleton.cliente.email)
            binding.inputDataNascimento.setText(ClienteSingleton.cliente.dataNascimento)

            binding.buttonProximo.text = "Salvar"
        }
        else{
            binding.inputCelular.setText(args.celular)
        }
    }

    private fun initValidationFields() = mapOf(
        CadastroViewModel.INPUT_NOME.first to binding.inputLayoutNome,
        CadastroViewModel.INPUT_CELULAR.first to binding.inputLayoutCelular,
        CadastroViewModel.INPUT_CPF.first to binding.inputLayoutCpf,
        CadastroViewModel.INPUT_EMAIL.first to binding.inputLayoutEmail,
        CadastroViewModel.INPUT_DATANASCIMENTO.first to binding.inputLayoutDataNascimento,
    )

    private fun listenToRegistrationStateEvent(validationFields: Map<String, TextInputLayout>) {
        cadastroViewModel.registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
            when (registrationState) {
                is CadastroViewModel.RegistroStatus.ColetarCredencial -> {
                    directions = ClienteFragmentDirections
                        .actionProfileDataFragmentToChooseCredentialsFragment("ClienteFragment")
                }
                is CadastroViewModel.RegistroStatus.AlterarCliente -> {
                    alteraCliente()
                }
                is CadastroViewModel.RegistroStatus.CadastroClienteInvalido -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
                else -> {}
            }
        }
    }

    private fun registerViewListeners() {

        binding.buttonProximo.setOnClickListener {
            cadastrarCliente()
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

    private fun cadastrarCliente() {
        val nome = binding.inputNome.text.toString()
        val celular = binding.inputCelular.text.toString().removeMask()
        val cpf = binding.inputCpf.text.toString()
        val email = binding.inputEmail.text.toString()
        val dataNascimento = binding.inputDataNascimento.text.toString()
        cadastroViewModel.cadastrarCliente(nome, celular, cpf, email, dataNascimento)
    }

    private fun cancelAuthentication() {
        directions = null
        cadastroViewModel.refuseAuthentication()
        binding.inputLayoutNome.dismissError()
        binding.inputLayoutCelular.dismissError()
        binding.inputLayoutCpf.dismissError()
        binding.inputLayoutEmail.dismissError()
        binding.inputLayoutDataNascimento.dismissError()
    }

    private fun alteraCliente() {
        cadastroViewModel.alteraCliente().observe(viewLifecycleOwner) {
            var result = it?.let { resultado ->
                when (resultado) {
                    is Resultado.Sucesso -> {
                        resultado.data?.let { cliente ->
                            ClienteSingleton.cliente = cadastroViewModel.cliente
                            ClienteSingleton.cliente.cadastrado = true
                            cliente
                        } ?: false
                    }
                    is Resultado.Erro -> {
                        false
                    }
                }
            } ?: false
            if (result) {
                Toast.makeText(activity, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show()

                navController.popBackStack(R.id.opcoesFragment, false)
            } else {
                Toast.makeText(
                    activity,
                    "Houve um erro ao tentar alterar os dados!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}