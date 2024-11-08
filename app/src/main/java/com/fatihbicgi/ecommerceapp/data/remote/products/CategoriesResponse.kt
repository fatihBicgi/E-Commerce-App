package com.fatihbicgi.ecommerceapp.data.remote.products

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String?
) : Serializable

data class Category(
    @SerializedName("name") // JSON'dan gelen "name" alanını "name" özelliğine eşleştiriyoruz.
    val name: String,
    @SerializedName("image") // JSON'dan gelen "image" alanını "image" özelliğine eşleştiriyoruz.
    val image: String
)