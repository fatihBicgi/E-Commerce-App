package com.fatihbicgi.ecommerceapp.scenes.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.login.LoginRequest
import com.fatihbicgi.ecommerceapp.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: LoginRepository
) : ViewModel() {

    val uiState = MutableStateFlow(
        LoginContract.UiState(
            email = "",
            password = "",
            isPasswordVisible = false,
        )
    )//state flow nedir

    fun onAction(action: LoginContract.UiAction) {
        when (action) {
            is LoginContract.UiAction.OnEmailChange -> onEmailChange(action.name)
            is LoginContract.UiAction.OnPasswordChange -> onPasswordChange(action.password)
            LoginContract.UiAction.OnLoginClick -> onLoginClick()
            LoginContract.UiAction.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
        }
    }

    private fun onEmailChange(email: String) {
        uiState.update {
            it.copy(email = email)
        }
    }

    private fun onPasswordChange(password: String) {
        uiState.update {
            it.copy(password = password)
        }
    }

    private fun onPasswordVisibilityChange() {
        uiState.update {
            it.copy(isPasswordVisible = it.isPasswordVisible.not())
        }
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            val request = LoginRequest(
                email = uiState.value.email,
                password = uiState.value.password
            )
            val response = repository.login(
                loginRequest = request
            )
            Log.i("apiResponse", response.toString())
        }
    }
}