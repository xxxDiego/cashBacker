package com.astetech.omnifidelidade.ui.cadastro

import androidx.lifecycle.*
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.network.PinNetwork
import com.astetech.omnifidelidade.network.response.ClientePostResponse
import com.astetech.omnifidelidade.repository.FidelidadeRepository
import com.astetech.omnifidelidade.repository.Resultado
import com.astetech.omnifidelidade.models.Config
import com.astetech.omnifidelidade.network.response.ValidaPinResponse
import com.astetech.omnifidelidade.singleton.ClienteSingleton


class CadastroViewModel : ViewModel() {

    sealed class RegistroStatus {
        object CadastrarCliente : RegistroStatus()
        object AlterarCliente : RegistroStatus()
        object ColetarCredencial : RegistroStatus()
        object RegistroCompleto : RegistroStatus()
        class CadastroClienteInvalido(val fields: List<Pair<String, Int>>) : RegistroStatus()
        class CredencialInvalida(val fields: List<Pair<String, Int>>) : RegistroStatus()
    }

    private val repository = FidelidadeRepository()

    private var _cliente = Cliente()

    val cliente : Cliente
    get() = _cliente


    private val _registrationStateEvent =
        MutableLiveData<RegistroStatus>(RegistroStatus.CadastrarCliente)

    val registrationStateEvent: LiveData<RegistroStatus>
        get() = _registrationStateEvent


    fun cadastrarCliente(
        nome: String,
        celular: String,
        cpf: String,
        email: String,
        dataNascimento: String
    ) {
        if (isValidCadastroCliente(nome, celular, cpf, email, dataNascimento)) {
            _cliente.nome = nome
            _cliente.celular = celular
            _cliente.cpf = cpf
            _cliente.email = email
            _cliente.dataNascimento = dataNascimento

            if (ClienteSingleton.cliente.cadastrado){
                _cliente.id = ClienteSingleton.cliente.id
                _registrationStateEvent.value = RegistroStatus.AlterarCliente
            }
            else{
                _registrationStateEvent.value = RegistroStatus.ColetarCredencial
            }

        }
    }

    fun CreateRegistrationCompleted() {
        _registrationStateEvent.value = RegistroStatus.RegistroCompleto
    }

    private fun isValidCadastroCliente(
        nome: String,
        celular: String,
        cpf: String,
        email: String,
        dataNascimento: String
    ): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()

        if (nome.isEmpty() || nome.length < 5 || nome.trim().split(" ").count() < 2) {
            invalidFields.add(INPUT_NOME)
        }
        if (celular.isEmpty() || celular.length != 11) {
            invalidFields.add(INPUT_CELULAR)
        }
        if (cpf.isEmpty() || cpf.length != 14) {
            invalidFields.add(INPUT_CPF)
        }
        if (email.isEmpty() || !email.contains("@") || !email.contains(".com")) {
            invalidFields.add(INPUT_EMAIL)
        }
        if (dataNascimento.isEmpty() || dataNascimento.length != 10) {
            invalidFields.add(INPUT_DATANASCIMENTO)
        }

        if (invalidFields.isNotEmpty()) {
            _registrationStateEvent.value = RegistroStatus.CadastroClienteInvalido(invalidFields)
            return false
        }
        return true
    }

    fun createCredentials(pin: String): Boolean {
        return isValidCredentials(pin)
    }

    private fun isValidCredentials(pin: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (pin.isEmpty() || pin.length != 4) {
            invalidFields.add(INPUT_PIN)
        }
        return if (invalidFields.isNotEmpty()) {
            _registrationStateEvent.value = RegistroStatus.CredencialInvalida(invalidFields)
            false
        } else {
            true
        }
    }

    fun refuseAuthentication() {
        _registrationStateEvent.value = RegistroStatus.CadastrarCliente
    }

    // network
    fun gravaCliente(): LiveData<Resultado<ClientePostResponse?>> =
        repository.gravaCliente(_cliente.clienteToNetwork())

    fun alteraCliente(): LiveData<Resultado<Boolean?>> =
        repository.alteraCliente(_cliente.clienteToNetwork())


    fun enviaPin(celular: String): LiveData<Resultado<Boolean?>> {
        val pinNetwork = PinNetwork(celular, Config.lojaId)
        return repository.enviaPin(pinNetwork)
    }

    fun validaPin(pin: String, celular: String): LiveData<Resultado<ValidaPinResponse?>> {
        val pinNetwork = PinNetwork(celular, Config.lojaId, pin)
        return repository.validaPin(pinNetwork)
    }

    companion object {
        val INPUT_NOME = "INPUT_NOME" to R.string.cliente_input_layout_error_nome
        val INPUT_CELULAR = "INPUT_CELULAR" to R.string.cliente_input_layout_error_celular
        val INPUT_CPF = "INPUT_CPF" to R.string.cliente_input_layout_error_cpf
        val INPUT_EMAIL = "INPUT_EMAIL" to R.string.cliente_input_layout_error_email
        val INPUT_DATANASCIMENTO =
            "INPUT_DATANASCIMENTO" to R.string.cliente_input_layout_error_datanascimento
        val INPUT_PIN = "INPUT_PIN" to R.string.credenciais_input_layout_error_pin
    }


}