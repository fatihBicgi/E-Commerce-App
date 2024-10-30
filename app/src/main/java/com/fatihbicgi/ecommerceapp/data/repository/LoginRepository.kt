package com.fatihbicgi.ecommerceapp.data.repository

import android.util.Log
import com.fatihbicgi.ecommerceapp.data.remote.login.LoginRequest
import com.fatihbicgi.ecommerceapp.data.remote.login.LoginResponse
import com.fatihbicgi.ecommerceapp.data.remote.login.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginService: LoginService
) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return loginService.login(loginRequest)
    }
}//repository local mi remote mu karar verir