package com.astetech.omnifidelidade.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astetech.omnifidelidade.R

class LoginViewModel : ViewModel() {

    sealed class AuthenticationState {
        object Unauthenticated : AuthenticationState()
        object Authenticated : AuthenticationState()
        object NotFound : AuthenticationState()
        class InvalidAuthentication(val fields: List<Pair<String, Int>>) : AuthenticationState()
    }

    var usuarioNome: String = ""
    var token: String = ""

    private val _authenticationStateEvent = MutableLiveData<AuthenticationState>()
    val authenticationStateEvent: LiveData<AuthenticationState>
        get() = _authenticationStateEvent

    init {
        refuseAuthentication()
    }

    fun refuseAuthentication() {
        _authenticationStateEvent.value = AuthenticationState.Unauthenticated
    }

    fun authenticateToken(token: String, username: String) {
        this.token = token
        this.usuarioNome = username
        _authenticationStateEvent.value = AuthenticationState.Authenticated
    }

    fun authenticate(usuarioCelular: String) {
        if (isValidForm(usuarioCelular)) {
            val usuario = buscaUsuario(usuarioCelular)
            if (usuario == "")
            {
                _authenticationStateEvent.value = AuthenticationState.NotFound
            }
            else{
                this.usuarioNome = usuario
                _authenticationStateEvent.value = AuthenticationState.Authenticated
            }
        }
    }

    private fun isValidForm(usuarioCelular: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (usuarioCelular.isEmpty() || usuarioCelular.length != 15) {
            invalidFields.add(INPUT_CELULAR)
        }
        if (invalidFields.isNotEmpty()) {
            _authenticationStateEvent.value =
                AuthenticationState.InvalidAuthentication(invalidFields)
            return false
        }
        return true
    }

    private fun buscaUsuario(celular: String): String{
        return if (celular == "(11) 99485-4564"){
            "Diego"
        } else {
            ""
        }
    }

    companion object {
        val INPUT_CELULAR = "INPUT_CELULAR" to R.string.login_input_layout_error_celular
    }
}