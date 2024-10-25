package com.fatihbicgi.ecommerceapp.scenes.login

sealed class LoginContract {

    data class UiState(
        val name: String,
        val surname: String,
        val password: String,
        val isPasswordVisible : Boolean
    )

    sealed class UiAction {
        class OnNameChange(
            val name: String,
        ) : UiAction()

        class OnSurnameChange(
            val surname: String,
        ) : UiAction()

        class OnPasswordChange(
            val password: String,
        ) : UiAction()

        data object OnPasswordVisibilityChange: UiAction()
        data object OnLoginClick : UiAction()
    }

}