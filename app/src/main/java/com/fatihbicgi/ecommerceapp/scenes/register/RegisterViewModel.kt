package com.fatihbicgi.ecommerceapp.scenes.register

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.register.RegisterRequest
import com.fatihbicgi.ecommerceapp.data.repository.RegisterRepository
import com.fatihbicgi.ecommerceapp.scenes.login.LoginContract
import com.fatihbicgi.ecommerceapp.util.NetworkMonitor
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val networkMonitor: NetworkMonitor,
    private val sharedPref: SharedPreferences,
) : ViewModel() {
    val uiState = MutableStateFlow(
        RegisterContract.UiState(
            email = "",
            password = "",
            name = "",
            phone = "",
            formattedPhone = "",
            address = "",
            isPasswordVisible = false,
            isRegisteredSuccessfuly = false,
        )
    )
    private val _userId = Channel<String>()
    val userId = _userId.receiveAsFlow()

    private val _uiEffect = Channel<RegisterContract.UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onAction(action: RegisterContract.UiAction) {
        when (action) {
            is RegisterContract.UiAction.OnEmailChange -> {
                onEmailChange(action.email)
            }

            is RegisterContract.UiAction.OnPasswordChange -> {
                OnPasswordChange(action.password)
            }

            is RegisterContract.UiAction.OnNameChange -> {
                onNameChange(action.name)
            }

            is RegisterContract.UiAction.OnPhoneChange -> {
                onPhoneChange(action.phone)
            }

            is RegisterContract.UiAction.OnAddressChange -> {
                onAddressChange(action.address)
            }

            RegisterContract.UiAction.OnPasswordVisibilityChange -> {
                onPasswordVisibilityChange()
            }

            RegisterContract.UiAction.OnRegisterClick -> {
                validateInputs()
            }
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
        // Temiz numarayı kaydet ve formatlı halini güncelle
        val cleanedPhone = phone.filter { it.isDigit() } // Sadece sayıları al
        val formatted = formatPhoneNumber(cleanedPhone)

        uiState.update {
            it.copy(
                phone = cleanedPhone,          // Backend için temiz numara
                formattedPhone = formatted     // UI için formatlı numara
            )
        }
    }

    private fun formatPhoneNumber(number: String): String {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val formatter = phoneUtil.getAsYouTypeFormatter("TR") // Türkiye formatı
        val formattedNumber = StringBuilder()
        number.forEach { char ->
            formattedNumber.append(formatter.inputDigit(char))
        }
        return formattedNumber.toString()
    }

    private fun onAddressChange(address: String) {
        uiState.update {
            it.copy(address = address)
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
        if (uiState.value.name.length < 2) {
            errors.add("Name must be at least 2 characters.")
        }

        if (uiState.value.email.length < 9 || !android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.value.email)
                .matches()
        ) {
            errors.add("Email must be at least 9 characters and valid.")
        }

        if (uiState.value.password.length < 9) {
            errors.add("Password must be at least 9 characters.")
        }

        if (uiState.value.address.length < 9) {
            errors.add("Address must be at least 9 characters.")
        }

        if (uiState.value.phone.length < 10) {
            errors.add("Phone must be at least 10 characters.")
        }
        if (networkMonitor.isNetworkAvailable().not()) {
            errors.add("Check your internet connection")
        }
        // Hataları güncelle
        uiState.update { currentState ->
            currentState.copy(validationErrors = errors)
        }
        // Eğer hata yoksa, kayıt işlemini gerçekleştir
        if (errors.isNotEmpty()) {
            viewModelScope.launch {
                _uiEffect.send(
                    RegisterContract.UiEffect.ShowToastMessage(
                        message = errors.joinToString()
                    )
                )
            }
        } else {
            onRegisterClick()
        }
    }

    private fun onRegisterClick() {
        viewModelScope.launch {
            val state = uiState.value
            val request = RegisterRequest(
                address = state.address,
                email = state.email,
                name = state.name,
                password = state.password,
                phone = state.phone,
            )
            val response = repository.register(
                registerRequest = request
            )
            if (response.status == 200) {
                _uiEffect.send(RegisterContract.UiEffect.NavigateToUserDetailScreen)
                _userId.send(response.userId.toString())
                with(sharedPref.edit()) {
                    putBoolean("rememberMe", true) // Remember me bilgisini kaydet
                    putString("userId", response.userId.toString()) // Kullanıcı ID'sini kaydet
                    apply() // Kaydetme işlemini tamamla
                }
                uiState.update {
                    it.copy(isRegisteredSuccessfuly = true)
                }
            }
        }
    }
}