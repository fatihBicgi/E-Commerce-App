package com.fatihbicgi.ecommerceapp.scenes.products

import com.fatihbicgi.ecommerceapp.data.remote.products.Category
import com.fatihbicgi.ecommerceapp.data.remote.products.Product
import com.fatihbicgi.ecommerceapp.scenes.login.LoginContract
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterContract.UiAction

object ProductDetailContract {
    data class UiState(
        val product: Product? = null,
        val errorMessage: String = ""
    )

    sealed class UiAction {
        data object OnProductClick : UiAction()
    }
}