package com.fatihbicgi.ecommerceapp.scenes.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenRoutes {
    @Serializable
    data object LoginScreen : ScreenRoutes

    @Serializable
    data object RegisterScreen : ScreenRoutes

    @Serializable
    data object SplashScreen : ScreenRoutes
}