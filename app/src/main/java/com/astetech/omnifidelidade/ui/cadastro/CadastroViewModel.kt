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


class CadastroViewModel : ViewModel() {


    sealed class RegistrationState {
        object CollectProfileData : RegistrationState()
        object CollectCredentials : RegistrationState()
        object RegistrationCompleted : RegistrationState()
        class InvalidProfileData(val fields: List<Pair<String, Int>>) : RegistrationState()
        class InvalidCredentials(val fields: List<Pair<String, Int>>) : RegistrationState()
    }

    private val repository = FidelidadeRepository()

    private val _registrationStateEvent =
        MutableLiveData<RegistrationState>(RegistrationState.CollectProfileData)
    val registrationStateEvent: LiveData<RegistrationState>
        get() = _registrationStateEvent

    var authToken = ""
        private set

    lateinit var cliente: Cliente

    fun collectProfileData(
        nome: String,
        celular: String,
        cpf: String,
        email: String,
        dataNascimento: String
    ) {
        if (isValidProfileData(nome, celular, cpf, email, dataNascimento)) {
            this.cliente = Cliente(
                nomeCliente = nome,
                celular = celular,
                cpf = cpf,
                emailCliente = email,
                dataNascimento = dataNascimento
            )
            _registrationStateEvent.value = RegistrationState.CollectCredentials
        }
    }

    fun gravaCliente(): LiveData<Resultado<ClientePostResponse?>> =
        repository.gravaCliente(this.cliente.clienteToNetwork())


    fun enviaPin(celular: String): LiveData<Resultado<Boolean?>> {
        var pin = PinNetwork(celular, Config.lojaId)
        return repository.enviaPin(pin)
    }

    fun CreateRegistrationCompleted() {
        _registrationStateEvent.value = RegistrationState.RegistrationCompleted
    }

    fun validaPin(pin: String): LiveData<Resultado<ValidaPinResponse?>> {
        var pin = PinNetwork(this.cliente.celular, Config.lojaId, pin)
        return repository.validaPin(pin)
    }

    private fun isValidProfileData(
        nome: String,
        celular: String,
        cpf: String,
        email: String,
        dataNascimento: String
    ): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()

        if (nome.isEmpty()) {
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
            _registrationStateEvent.value = RegistrationState.InvalidProfileData(invalidFields)
            return false
        }
        return true
        enviaPin(celular)
    }

    fun createCredentials(pin: String):Boolean{
       return isValidCredentials(pin)
    }

    private fun isValidCredentials(pin: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (pin.isEmpty() || pin.length != 4) {
            invalidFields.add(INPUT_PIN)
        }
        return if (invalidFields.isNotEmpty()) {
            _registrationStateEvent.value = RegistrationState.InvalidCredentials(invalidFields)
            false
        } else {
            true
        }
    }

    fun refuseAuthentication() {
        _registrationStateEvent.value = RegistrationState.CollectProfileData
    }

    fun userCancelledRegistration(): Boolean {
        authToken = ""
        _registrationStateEvent.value = RegistrationState.CollectProfileData
        return true
    }

    companion object {
        val INPUT_NOME = "INPUT_NOME" to R.string.profile_data_input_layout_error_nome
        val INPUT_CELULAR = "INPUT_CELULAR" to R.string.profile_data_input_layout_error_celular
        val INPUT_CPF = "INPUT_CPF" to R.string.profile_data_input_layout_error_cpf
        val INPUT_EMAIL = "INPUT_EMAIL" to R.string.profile_data_input_layout_error_email
        val INPUT_DATANASCIMENTO ="INPUT_DATANASCIMENTO" to R.string.profile_data_input_layout_error_datanascimento
        val INPUT_PIN = "INPUT_PIN" to R.string.choose_credentials_input_layout_error_pin
    }

}