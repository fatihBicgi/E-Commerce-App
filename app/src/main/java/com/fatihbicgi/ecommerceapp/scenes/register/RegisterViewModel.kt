package com.fatihbicgi.ecommerceapp.scenes.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.util.AuthStates
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    lateinit var auth: FirebaseAuth
    val authState = MutableLiveData<AuthStates>()

    val uiState = MutableStateFlow(
        RegisterContract.UiState(
            email = "",
            password = "",
            name = "",
            phone = "",
            address = "",
        )
    )//state flow nedir

    fun onAction(action: RegisterContract.UiAction) {
        when (action) {
            RegisterContract.UiAction.OnRegisterClick -> TODO()
            is RegisterContract.UiAction.OnEmailChange -> onEmailChange(action.email)
            is RegisterContract.UiAction.OnPasswordChange -> OnPasswordChange(action.password)
            is RegisterContract.UiAction.OnNameChange -> onNameChange(action.name)
            is RegisterContract.UiAction.OnPhoneChange -> onPhoneChange(action.phone)
            is RegisterContract.UiAction.OnAddressChange -> onAddressChange(action.address)
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

    fun registerWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                authState.value = AuthStates.LOADING
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        authState.value = AuthStates.SUCCESS
                    } else {
                        authState.value = AuthStates.FAILED
                    }
                }
            } catch (e: Exception) {
                authState.value = AuthStates.FAILED
            }
        }
    }
}