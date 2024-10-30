package com.fatihbicgi.ecommerceapp.data.repository

import com.fatihbicgi.ecommerceapp.data.remote.register.RegisterRequest
import com.fatihbicgi.ecommerceapp.data.remote.register.RegisterResponse
import com.fatihbicgi.ecommerceapp.data.remote.register.RegisterService
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val registerService: RegisterService,
) {
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return registerService.register(registerRequest)
    }
} //repository local mi remote mu karar verir