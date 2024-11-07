package com.fatihbicgi.ecommerceapp.data.remote.products


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("status")
    val status: Int?
): Serializable