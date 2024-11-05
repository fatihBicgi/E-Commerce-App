package com.fatihbicgi.ecommerceapp.scenes.login

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.login.LoginRequest
import com.fatihbicgi.ecommerceapp.data.repository.LoginRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val shredPref: SharedPreferences,
) : ViewModel() {

    val uiState = MutableStateFlow(
        LoginContract.UiState(
            email = "",
            password = "",
            isPasswordVisible = false,
            rememberMe = false,
            isLoginSuccessfuly = false,
        )
    )
    private val _userId = Channel<String>()
    val userId = _userId.receiveAsFlow()

    private val _validationMessages = Channel<String>()
    val validationMessages = _validationMessages.receiveAsFlow()

    // Mesajları bir seferlik dinlemek için

    fun onAction(action: LoginContract.UiAction) {
        when (action) {
            is LoginContract.UiAction.OnEmailChange -> onEmailChange(action.name)
            is LoginContract.UiAction.OnPasswordChange -> onPasswordChange(action.password)
            LoginContract.UiAction.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
            LoginContract.UiAction.OnRememberMeChange -> onRememberMeChange()
            LoginContract.UiAction.OnLoginClick -> {
                validateInputs()
            }
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

    private fun onRememberMeChange() {
        uiState.update {
            it.copy(rememberMe = it.rememberMe.not())
        }
    }

    private fun onPasswordVisibilityChange() {
        uiState.update {
            it.copy(isPasswordVisible = it.isPasswordVisible.not())
        }
    }

    private fun validateInputs() {
        // Öncelikle eski hata mesajlarını temizle
        uiState.update { currentState ->
            currentState.copy(validationErrors = emptyList()) // Eski hataları temizle
        }
        val errors = mutableListOf<String>()
        if (uiState.value.email.length < 9 || !Patterns.EMAIL_ADDRESS.matcher(uiState.value.email)
                .matches()
        ) {
            errors.add("Email must be at least 9 characters and valid.")
        }

        if (uiState.value.password.length < 9) {
            errors.add("Password must be at least 9 characters.")
        }
        // Hataları güncelle
        uiState.update { currentState ->
            currentState.copy(validationErrors = errors)
        }

        // Eğer hata yoksa, kayıt işlemini gerçekleştir
        if (errors.isNotEmpty()) {
            viewModelScope.launch {
                errors.forEach { _validationMessages.send(it) }
            }
        } else {
            onLoginClick() // Hata yoksa login işlemi
        }
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            val state = uiState.value //ctrl+alt+v kısayolu
            val request = LoginRequest(
                email = state.email,
                password = state.password
            )
            val response = repository.login(
                loginRequest = request
            )
            if (response.status == 200) {
                //200 dönerse sharedprefe o anki remember me değerini yazdır
                _userId.send(response.userId.toString())
                uiState.update {
                    it.copy(isLoginSuccessfuly = true)
                }

            } else
                _validationMessages.send("User not found")
            uiState.update { currentState ->
                currentState.copy(
                    validationErrors = listOf("User not found")
                )
            }
            Log.i("apiResponse", response.toString())
        }
    }
}