package com.fatihbicgi.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fatihbicgi.ecommerceapp.login.LoginScreen
import com.fatihbicgi.ecommerceapp.register.RegisterScreen
import com.fatihbicgi.ecommerceapp.navigation.ScreenRoutes
import com.fatihbicgi.ecommerceapp.splash.SplashScreen
import com.fatihbicgi.ecommerceapp.ui.theme.ECommerceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECommerceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(50.dp),

                        ) {
                        Navigation(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenRoutes.SplashScreen
    ) {
        composable<ScreenRoutes.SplashScreen> {
            SplashScreen(
                onGoToLoginScreen = {
                    navController.navigate(ScreenRoutes.LoginScreen)
                }, onGoToRegisterScreen = {
                    navController.navigate(ScreenRoutes.RegisterScreen)
                })
        }
        composable<ScreenRoutes.LoginScreen> {
            LoginScreen()
        }
        composable<ScreenRoutes.RegisterScreen> {
            RegisterScreen()
        }

    }
}


