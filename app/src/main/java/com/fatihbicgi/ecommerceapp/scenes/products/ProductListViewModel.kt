package com.fatihbicgi.ecommerceapp.scenes.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListContract.UiState())
    val uiState: StateFlow<ProductListContract.UiState> = _uiState

    private val _uiEffect = Channel<ProductListContract.UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    // Aksiyonları dinleyip gerekli işlemi yapacak
    fun onAction(action: ProductListContract.UiAction) {
        when (action) {
            is ProductListContract.UiAction.LoadProducts -> loadProducts(action.storeName)
            is ProductListContract.UiAction.LoadSaleProducts -> loadSaleProducts(action.storeName)
            is ProductListContract.UiAction.LoadCategoryProducts -> loadCategoryProducts(
                action.storeName,
                action.category
            )
        }
    }

    init {
        loadProducts("canerture") // Mağaza ismini buraya girin
        loadSaleProducts("canerture") // İndirimli ürünler
        loadCategoryProducts(
            "canerture",
            category = "Notebook"
        )
    }

    private fun loadProducts(storeName: String) {
        viewModelScope.launch {
            try {
                val response = productRepository.getProducts(storeName)
                if (response.status == 200) {
                    _uiState.update { it.copy(products = response.products) }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = response.message ?: "Bilinmeyen bir hata oluştu"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Veri yüklenemedi: ${e.localizedMessage}") }
            }
        }
    }

    private fun loadSaleProducts(storeName: String) {
        viewModelScope.launch {
            try {
                val response = productRepository.getSaleProducts(storeName)
                if (response.status == 200) {
                    _uiState.update { it.copy(saleProducts = response.products) }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = response.message ?: "Bilinmeyen bir hata oluştu"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Veri yüklenemedi: ${e.localizedMessage}") }
            }
        }
    }

    private fun loadCategoryProducts(storeName: String, category: String) {
        viewModelScope.launch {
            try {
                val response = productRepository.getProductsByCategory(storeName, category)
                if (response.status == 200) {
                    _uiState.update { it.copy(categoryProducts = response.products) }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = response.message ?: "Bilinmeyen bir hata oluştu"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Veri yüklenemedi: ${e.localizedMessage}") }
            }
        }
    }
}