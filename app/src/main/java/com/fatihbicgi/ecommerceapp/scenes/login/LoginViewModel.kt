package com.fatihbicgi.ecommerceapp.scenes.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val uiState = MutableStateFlow(LoginContract.UiState(name = "", surname = ""))//state flow nedir

    fun onAction(action: LoginContract.UiAction) {
        when (action) {
            is LoginContract.UiAction.OnNameChange -> onNameChange(action.name)
            is LoginContract.UiAction.OnSurnameChange -> onSurnameChange(action.surname)
            LoginContract.UiAction.OnLoginClick -> TODO()
        }
    }

    private fun onNameChange(name: String) {
        uiState.update {
            it.copy(name = name)
        }
    }

    private fun onSurnameChange(surname: String) {
        uiState.update {
            it.copy(surname = surname)
        }
    }
}