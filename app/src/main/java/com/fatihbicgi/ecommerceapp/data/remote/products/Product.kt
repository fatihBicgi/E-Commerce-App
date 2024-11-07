package com.fatihbicgi.ecommerceapp.data.remote.products


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("category")
    val category: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageOne")
    val imageOne: String?,
    @SerializedName("imageThree")
    val imageThree: String?,
    @SerializedName("imageTwo")
    val imageTwo: String?,
    @SerializedName("price")
    val price: Float,
    @SerializedName("rate")
    val rate: Float?,
    @SerializedName("salePrice")
    val salePrice: Float,
    @SerializedName("saleState")
    val saleState: Boolean,
    @SerializedName("title")
    val title: String,
) : Serializable