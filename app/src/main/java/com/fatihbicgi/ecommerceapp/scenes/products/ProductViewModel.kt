package com.fatihbicgi.ecommerceapp.scenes.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.products.Product
import com.fatihbicgi.ecommerceapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductsRepository
) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set
    var errorMessage by mutableStateOf("")

    init {
        getProducts("canerture") // Mağaza ismini buraya girin
    }

    private fun getProducts(storeName: String) {
        viewModelScope.launch {
            try {
                val response = productRepository.getProducts(storeName)
                if (response.status == 200) {
                    products = response.products
                } else {
                    errorMessage = response.message ?: "Bilinmeyen bir hata oluştu"
                }
            } catch (e: Exception) {
                errorMessage = "Veri yüklenemedi: ${e.localizedMessage}"
            }
        }
    }
}