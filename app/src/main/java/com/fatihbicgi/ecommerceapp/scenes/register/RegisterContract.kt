package com.fatihbicgi.ecommerceapp.scenes.register

sealed class RegisterContract {

    data class UiState(
        val email: String,
        val password: String,
        val name: String,
        val phone: String,
        val formattedPhone: String,
        val address: String,
        val isPasswordVisible: Boolean,
        val validationErrors: List<String> = emptyList(),
    )

    sealed class UiAction {
        class OnEmailChange(
            val email: String,
        ) : UiAction()

        class OnPasswordChange(
            val password: String,
        ) : UiAction()

        class OnNameChange(
            val name: String,
        ) : UiAction()

        class OnPhoneChange(
            val phone: String,
        ) : UiAction()

        class OnAddressChange(
            val address: String,
        ) : UiAction()

        data object OnPasswordVisibilityChange : UiAction()
        data object OnRegisterClick : UiAction()
    }
}