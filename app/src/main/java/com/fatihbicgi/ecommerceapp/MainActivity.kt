package com.fatihbicgi.ecommerceapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fatihbicgi.ecommerceapp.scenes.login.LoginScreen
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterScreen
import com.fatihbicgi.ecommerceapp.scenes.navigation.ScreenRoutes
import com.fatihbicgi.ecommerceapp.scenes.login.LoginViewModel
import com.fatihbicgi.ecommerceapp.scenes.navigation.Navigation
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterViewModel
import com.fatihbicgi.ecommerceapp.scenes.splash.SplashScreen
import com.fatihbicgi.ecommerceapp.ui.theme.ECommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("ecommerce-sharedpref", Context.MODE_PRIVATE)
        setContent {
            ECommerceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(50.dp),
                    ) {
                        Navigation(
                            modifier = Modifier.padding(innerPadding),
                            sharedPref = sharedPref,
                        )
                    }
                }
            }
        }
    }
}



