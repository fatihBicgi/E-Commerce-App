package com.fatihbicgi.ecommerceapp.scenes.products

import com.fatihbicgi.ecommerceapp.data.remote.products.Product

sealed class ProductListContract {

    // UiState: Ekranın durumunu tutar
    data class UiState(
        val products: List<Product> = emptyList(),
        val saleProducts: List<Product> = emptyList(),
        val categoryProducts: List<Product> = emptyList(),
        val errorMessage: String = ""
    )

    // UiAction: Kullanıcının gerçekleştirdiği aksiyonları temsil eder
    sealed class UiAction {
        data class LoadProducts(val storeName: String) : UiAction()
        data class LoadSaleProducts(val storeName: String) : UiAction()
        data class LoadCategoryProducts(val storeName: String, val category: String) : UiAction()
    }

    // UiEffect: UI üzerinde tek seferlik gerçekleşen efektler (toast mesajları, navigation gibi)
    sealed class UiEffect {
        data class ShowErrorMessage(val message: String) : UiEffect()
        object NavigateToProductDetailScreen : UiEffect()
    }
}