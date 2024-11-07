package com.fatihbicgi.ecommerceapp.data.repository

import com.fatihbicgi.ecommerceapp.data.remote.products.ProductsResponse
import com.fatihbicgi.ecommerceapp.data.remote.products.ProductsService
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService
) {
    suspend fun getProducts(store: String): ProductsResponse {
        return productsService.getProducts(store)
    }
}