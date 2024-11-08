package com.fatihbicgi.ecommerceapp.scenes.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.products.CategoriesResponse
import com.fatihbicgi.ecommerceapp.data.remote.products.ProductsResponse
import com.fatihbicgi.ecommerceapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    init {
        loadDataConcurrently("canerture")
    }

    //ayrı istekler ama tek seferde birlikte ui ekranı güncellemek
    private fun loadDataConcurrently(storeName: String) {
        viewModelScope.launch {
            try {
                // Launching the API calls concurrently
                val results = awaitAll(
                    async { productRepository.getProducts(storeName) },
                    async { productRepository.getSaleProducts(storeName) },
                    async { productRepository.getProductsByCategory(storeName, "Notebook") },
                    async { productRepository.getCategories(storeName) }
                )

                // Process the results once all requests are finished
                val productsResponse = results[0] as ProductsResponse
                val saleProductsResponse = results[1] as ProductsResponse
                val categoryProductsResponse = results[2] as ProductsResponse
                val categoriesResponse = results[3] as CategoriesResponse

                if (productsResponse.status == 200) {
                    _uiState.update { it.copy(products = productsResponse.products) }
                }
                if (saleProductsResponse.status == 200) {
                    _uiState.update { it.copy(saleProducts = saleProductsResponse.products) }
                }
                if (categoryProductsResponse.status == 200) {
                    _uiState.update { it.copy(categoryProducts = categoryProductsResponse.products) }
                }
                if (categoriesResponse.status == 200) {
                    _uiState.update { it.copy(categories = categoriesResponse.categories) }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Veri yüklenemedi: ${e.localizedMessage}") }
            }
        }
    }


//paralel istekler ile ekranı güncellemek

    /*    private fun loadProducts(storeName: String) {
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

        private fun loadCategories(storeName: String) {
            viewModelScope.launch {
                try {
                    val response = productRepository.getCategories(storeName)
                    if (response.status == 200) {
                        _uiState.update { it.copy(categories = response.categories) }
                    } else {
                        _uiState.update {
                            it.copy(
                                errorMessage = response.message ?: "Error fetching categories"
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = "Error fetching categories: ${e.localizedMessage}") }
                }
            }
        }*/
}