package com.fatihbicgi.ecommerceapp.data.remote.register


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterRequest(
    @SerializedName("address")
    val address: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?
): Serializable