package com.fatihbicgi.ecommerceapp.scenes.register


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.register.RegisterRequest
import com.fatihbicgi.ecommerceapp.data.repository.RegisterRepository
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
            formattedPhone = "",
            address = "",
            isPasswordVisible = false,
        )
    )
//ui effect, viewmodeldeki bir aksiyon bir kez çalıştırılması gereken bir şey için kullanılır
//channel ve sharedflow stateflow araştır, bir kere çalışmasını istediğimiz bir kod

    private val _isRegisteredSuccessfully = MutableStateFlow(false)
    val isRegisteredSuccessfully: StateFlow<Boolean> = _isRegisteredSuccessfully

    private val _userId = MutableStateFlow<String>("empty")
    val userId: StateFlow<String> = _userId

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
        // Hataları güncelle
        uiState.update { currentState ->
            currentState.copy(validationErrors = errors)
        }
        // Eğer hata yoksa, kayıt işlemini gerçekleştir
        if (errors.isEmpty()) {
            Log.i(
                "Registration",
                "Registration successful!"
            )
            onRegisterClick()
        } else {
            Log.i("Registration", "Errors found: ${errors.joinToString()}")
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
                _userId.value = response.userId.toString()
                _isRegisteredSuccessfully.value = true
                delay(200)
            } else _isRegisteredSuccessfully.value = false
        }
    }
}