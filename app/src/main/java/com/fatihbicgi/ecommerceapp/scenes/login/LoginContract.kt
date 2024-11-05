package com.fatihbicgi.ecommerceapp.scenes.login

sealed class LoginContract {

    data class UiState(
        val email: String,
        val password: String,
        val isPasswordVisible: Boolean,
        val rememberMe: Boolean,
        val isLoginSuccessfuly: Boolean,
        val validationErrors: List<String> = emptyList(),
    )

    sealed class UiAction {
        class OnEmailChange(
            val name: String,
        ) : UiAction()

        class OnPasswordChange(
            val password: String,
        ) : UiAction()

        data object OnPasswordVisibilityChange : UiAction()
        data object OnRememberMeChange : UiAction()
        data object OnLoginClick : UiAction()
    }

}