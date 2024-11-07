package com.fatihbicgi.ecommerceapp.data.remote.products

import retrofit2.http.GET
import retrofit2.http.Header

interface ProductsService {
    @GET("get_products")
    suspend fun getProducts(
        @Header("store") store: String
    ): ProductsResponse
}