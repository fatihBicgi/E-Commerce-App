package com.fatihbicgi.ecommerceapp.data.remote.register


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("userId")
    val userId: String?
) : Serializable