package com.fatihbicgi.ecommerceapp.scenes.register

sealed class RegisterContract {

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

        data object OnRegisterClick : UiAction()
    }

}