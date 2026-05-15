package com.rafaelleal.kotlinandroidfundamentals.architectures.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelleal.kotlinandroidfundamentals.architectures.views.models.LoginEffect
import com.rafaelleal.kotlinandroidfundamentals.architectures.views.models.LoginIntent
import com.rafaelleal.kotlinandroidfundamentals.architectures.views.models.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MVIViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email)
            }
            is LoginIntent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password)
            }
            is LoginIntent.LoginClicked -> {
                executeLogin()
            }
        }
    }

    private fun executeLogin() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            // Mock de chamada de rede
            delay(2000)

            if (_state.value.email == "admin@teste.com" && _state.value.password == "1234") {
                _state.value = _state.value.copy(isLoading = false, isLoggedIn = true)
                _effect.emit(LoginEffect.NavigateToHome)
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "E-mail ou senha inválidos!"
                )
            }
        }
    }
}