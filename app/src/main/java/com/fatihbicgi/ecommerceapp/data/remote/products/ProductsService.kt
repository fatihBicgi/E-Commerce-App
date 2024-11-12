package com.fatihbicgi.ecommerceapp.data.remote.products

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductsService {

    @GET("get_products")
    suspend fun getProducts(
        @Header("store") store: String
    ): ProductsResponse

    @GET("get_sale_products")
    suspend fun getSaleProducts(
        @Header("store") store: String
    ): ProductsResponse

    @GET("get_products_by_category")
    suspend fun getProductsByCategory(
        @Header("store") store: String,
        @Query("category") category: String
    ): ProductsResponse

    @GET("get_product_detail")
    suspend fun getProductDetail(
        @Header("store") store: String,
        @Query("id") productId: Int
    ): ProductDetailResponse


    @GET("get_categories")
    suspend fun getCategories(
        @Header("store") store: String
    ): CategoriesResponse
}