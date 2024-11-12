package com.fatihbicgi.ecommerceapp.data.remote.products

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductDetailResponse(
    @SerializedName("product")
    val product: Product,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String?
) : Serializable
