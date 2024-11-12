package com.fatihbicgi.ecommerceapp.scenes.navigation

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

sealed interface ScreenRoutes {
    @Serializable
    data object LoginScreen : ScreenRoutes

    @Serializable
    data object RegisterScreen : ScreenRoutes

    @Serializable
    data object SplashScreen : ScreenRoutes

    @Serializable
    data class UserDetailScreen(val id: String) : ScreenRoutes

    @Serializable
    data object ProductListScreen : ScreenRoutes

    @Serializable
    data class ProductDetailScreen(val id: Int) : ScreenRoutes
}

//data buradan aktarılabilir mi?