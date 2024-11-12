package com.fatihbicgi.ecommerceapp.scenes.products

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.remote.login.LoginRequest
import com.fatihbicgi.ecommerceapp.data.remote.products.Product
import com.fatihbicgi.ecommerceapp.data.repository.ProductsRepository
import com.fatihbicgi.ecommerceapp.scenes.login.LoginContract
import com.fatihbicgi.ecommerceapp.scenes.userdetail.UserDetailContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val storeName = "canerture"

    private val _uiState = MutableStateFlow(ProductDetailContract.UiState())
    val uiState: StateFlow<ProductDetailContract.UiState> = _uiState

    private val productId = checkNotNull(savedStateHandle.get<Int>("id"))

    init {
        getProductDetail()
        Log.i("Product Id init", productId.toString())
    }

    /*private val _productId = Channel<Int>()
    val productId = _productId.receiveAsFlow()*/

    private fun getProductDetail() {
        viewModelScope.launch {
            try {
                val response = repository.getProductDetail(
                    store = storeName,
                    productId = productId
                )
                if (response.status == 200) {
                    _uiState.update { it.copy(product = response.product) }
                    //_productId.send(response.product.id)
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



