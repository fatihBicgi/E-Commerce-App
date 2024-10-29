package com.fatihbicgi.ecommerceapp.scenes.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.register.RegisterRequest
import com.fatihbicgi.ecommerceapp.domain.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
) : ViewModel() {

    val uiState = MutableStateFlow(
        RegisterContract.UiState(
            email = "",
            password = "",
            name = "",
            phone = "",
            address = "",
            isPasswordVisible = false,
        )
    )//state flow nedir

    fun onAction(action: RegisterContract.UiAction) {
        when (action) {
            RegisterContract.UiAction.OnRegisterClick -> onRegisterClick()
            is RegisterContract.UiAction.OnEmailChange -> onEmailChange(action.email)
            is RegisterContract.UiAction.OnPasswordChange -> OnPasswordChange(action.password)
            is RegisterContract.UiAction.OnNameChange -> onNameChange(action.name)
            is RegisterContract.UiAction.OnPhoneChange -> onPhoneChange(action.phone)
            is RegisterContract.UiAction.OnAddressChange -> onAddressChange(action.address)
            RegisterContract.UiAction.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
        }
    }

    private fun onEmailChange(email: String) {
        uiState.update {
            it.copy(email = email)
        }
    }

    private fun OnPasswordChange(password: String) {
        uiState.update {
            it.copy(password = password)
        }
    }

    private fun onNameChange(name: String) {
        uiState.update {
            it.copy(name = name)
        }
    }

    private fun onPhoneChange(phone: String) {
        uiState.update {
            it.copy(phone = phone)
        }
    }

    private fun onAddressChange(address: String) {
        uiState.update {
            it.copy(address = address)
        }
    }

    private fun onRegisterClick() {
        viewModelScope.launch {
            val request = RegisterRequest(
                address = uiState.value.address,
                email = uiState.value.email,
                name = uiState.value.name,
                password = uiState.value.password,
                phone = uiState.value.phone
            )
            val response = repository.register(
                registerRequest = request
            )
            Log.i("apiResponse", response.toString())
        }
    }

    private fun onPasswordVisibilityChange() {
        uiState.update {
            it.copy(isPasswordVisible = it.isPasswordVisible.not())
        }
    }


}