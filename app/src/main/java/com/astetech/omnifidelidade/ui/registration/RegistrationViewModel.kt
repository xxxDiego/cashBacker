package com.astetech.omnifidelidade.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astetech.omnifidelidade.R

class RegistrationViewModel : ViewModel() {

    sealed class RegistrationState {
        object CollectProfileData :  RegistrationState()
        object CollectCredentials : RegistrationState()
        object RegistrationCompleted : RegistrationState()
        class InvalidProfileData(val fields: List<Pair<String, Int>>) : RegistrationState()
        class InvalidCredentials(val fields: List<Pair<String, Int>>) : RegistrationState()
    }

    private val _registrationStateEvent = MutableLiveData<RegistrationState>(RegistrationState.CollectProfileData)
    val registrationStateEvent: LiveData<RegistrationState>
        get() = _registrationStateEvent

    var authToken = ""
        private set

    var authNome = ""
        private set

    fun collectProfileData(nome: String,
                           celular: String,
                           cpf: String,
                           email: String,
                           dataNascimento: String){
        if (isValidProfileData(nome, celular, cpf, email, dataNascimento)) {
            // Persist data
            this.authNome = nome
            _registrationStateEvent.value = RegistrationState.CollectCredentials
        }
    }

    private fun isValidProfileData(nome: String,
                                   celular: String,
                                   cpf: String,
                                   email: String,
                                   dataNascimento: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()

        if (nome.isEmpty()) {
            invalidFields.add(INPUT_NOME)
        }
        if (celular.isEmpty() || celular.length != 15) {
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
    }

    fun createCredentials(pin: String) {
        if (isValidCredentials(pin)) {
            // ... create account
            // ... authenticate
            this.authToken = "token"
            _registrationStateEvent.value = RegistrationState.RegistrationCompleted
        }
    }

    private fun isValidCredentials(pin: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (pin.isEmpty() || pin != "8888") {
            invalidFields.add(INPUT_PIN)
        }
        if (invalidFields.isNotEmpty()) {
            _registrationStateEvent.value = RegistrationState.InvalidCredentials(invalidFields)
            return false
        }
        return true
    }

    fun userCancelledRegistration() : Boolean {
        authToken = ""
        _registrationStateEvent.value = RegistrationState.CollectProfileData
        return true
    }

    companion object {
        val INPUT_NOME = "INPUT_NOME" to R.string.profile_data_input_layout_error_nome
        val INPUT_CELULAR = "INPUT_CELULAR" to R.string.profile_data_input_layout_error_celular
        val INPUT_CPF = "INPUT_CPF" to R.string.profile_data_input_layout_error_cpf
        val INPUT_EMAIL = "INPUT_EMAIL" to R.string.profile_data_input_layout_error_email
        val INPUT_DATANASCIMENTO = "INPUT_DATANASCIMENTO" to R.string.profile_data_input_layout_error_datanascimento
        val INPUT_PIN = "INPUT_PIN" to R.string.choose_credentials_input_layout_error_pin
    }
}