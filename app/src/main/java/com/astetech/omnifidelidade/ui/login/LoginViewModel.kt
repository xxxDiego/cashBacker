package com.astetech.omnifidelidade.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.models.Cliente
import com.astetech.omnifidelidade.network.FidelidadeNetwork
import com.astetech.omnifidelidade.network.FidelidadeService
import com.astetech.omnifidelidade.repository.FidelidadeRepository
import com.astetech.omnifidelidade.repository.Resultado
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginViewModel() : ViewModel() {

    private val TAG = "LoginViewModel"

    private val repository =  FidelidadeRepository()

    private var _cliente = MutableLiveData<Cliente>()
    val cliente: LiveData<Cliente>
        get() = _cliente

    private var usuarioNome: String = ""

    sealed class AuthenticationState {
        object Unauthenticated : AuthenticationState()
        object Authenticated : AuthenticationState()
        object NotFound : AuthenticationState()
        class InvalidAuthentication(val fields: List<Pair<String, Int>>) : AuthenticationState()
    }

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
        this.usuarioNome = username
        _authenticationStateEvent.value = AuthenticationState.Authenticated
    }

    fun authenticate(usuarioCelular: String):Boolean {
        return isValidForm(usuarioCelular)
    }

    
    fun buscaUsuario(celular: String): LiveData<Resultado<Cliente?>> =
        repository.buscaCliente(celular)

    private fun isValidForm(usuarioCelular: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (usuarioCelular.isEmpty() || usuarioCelular.length != 11) {
            invalidFields.add(INPUT_CELULAR)
        }
        if (invalidFields.isNotEmpty()) {
            _authenticationStateEvent.value =
                AuthenticationState.InvalidAuthentication(invalidFields)
            return false
        }
        return true
    }

    companion object {
        val INPUT_CELULAR = "INPUT_CELULAR" to R.string.login_input_layout_error_celular
    }
}