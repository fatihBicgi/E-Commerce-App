package com.fatihbicgi.ecommerceapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build

class NetworkMonitor(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}