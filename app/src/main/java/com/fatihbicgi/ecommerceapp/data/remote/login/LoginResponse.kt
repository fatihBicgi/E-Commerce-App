package com.fatihbicgi.ecommerceapp.data.remote.login


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("userId")
    val userId: String?
) : Serializable