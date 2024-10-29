package com.fatihbicgi.ecommerceapp.data.remote.register

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("sign_up")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}