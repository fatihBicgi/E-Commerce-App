package com.fatihbicgi.ecommerceapp.scenes.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    val uiState = MutableStateFlow(RegisterContract.UiState(name = "", surname = ""))//state flow nedir

    fun onAction(action: RegisterContract.UiAction) {
        when (action) {
            is RegisterContract.UiAction.OnNameChange -> onNameChange(action.name)
            is RegisterContract.UiAction.OnSurnameChange -> onSurnameChange(action.surname)
            RegisterContract.UiAction.OnRegisterClick -> TODO()
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