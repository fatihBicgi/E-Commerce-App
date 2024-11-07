package com.fatihbicgi.ecommerceapp.scenes.userdetail

sealed class UserDetailContract {
    sealed class UiEffect {
        object NavigateToLoginScreen : UiEffect()
    }

    sealed class OnAction {
        object Logout : OnAction()
    }
}