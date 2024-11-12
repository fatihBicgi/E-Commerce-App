package com.fatihbicgi.ecommerceapp.scenes.userdetail

sealed class UserDetailContract {

    data class UiState(
        val userId: String,
    )

    sealed class UiEffect {
        object NavigateToLoginScreen : UiEffect()
    }

    sealed class OnAction {
        object Logout : OnAction()
    }
}