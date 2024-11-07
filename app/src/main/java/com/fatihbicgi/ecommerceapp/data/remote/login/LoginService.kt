package com.fatihbicgi.ecommerceapp.data.remote.login

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("sign_in")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse
}