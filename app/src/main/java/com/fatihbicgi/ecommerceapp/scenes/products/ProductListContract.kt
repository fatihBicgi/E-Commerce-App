package com.fatihbicgi.ecommerceapp.scenes.products

import com.fatihbicgi.ecommerceapp.data.remote.products.Category
import com.fatihbicgi.ecommerceapp.data.remote.products.Product

sealed class ProductListContract {

    // UiState: Ekranın durumunu tutar
    data class UiState(
        val products: List<Product> = emptyList(),
        val saleProducts: List<Product> = emptyList(),
        val categories: List<Category> = emptyList(),
        val categoryProducts: List<Product> = emptyList(),
        val selectedProduct: Product? = null,
        val errorMessage: String = ""
    )

    sealed class UiAction {
        class OnProductClicked(
            val id: Int,
        ) : UiAction()
    }

    // UiEffect: UI üzerinde tek seferlik gerçekleşen efektler (toast mesajları, navigation gibi)
    sealed class UiEffect {
        data class ShowErrorMessage(val message: String) : UiEffect()
        data class NavigateToProductDetailScreen(
            val productId: Int
        ) : UiEffect()

    }
}