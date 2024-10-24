package com.fatihbicgi.ecommerceapp.scenes.login

sealed class LoginContract {

    data class UiState(
        val name: String,
        val surname: String
    )

    sealed class UiAction {
        class OnNameChange(
            val name: String,
        ) : UiAction()

        class OnSurnameChange(
            val surname: String,
        ) : UiAction()

        data object OnLoginClick : UiAction()
    }

}