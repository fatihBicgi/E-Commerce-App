package com.fatihbicgi.ecommerceapp.scenes.login

sealed class LoginContract {

    data class UiState(
        val email: String,
        val password: String,
        val isPasswordVisible: Boolean
    )

    sealed class UiAction {
        class OnEmailChange(
            val name: String,
        ) : UiAction()

        class OnPasswordChange(
            val password: String,
        ) : UiAction()

        data object OnPasswordVisibilityChange : UiAction()
        data object OnLoginClick : UiAction()
    }

}