package com.fatihbicgi.ecommerceapp.scenes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fatihbicgi.ecommerceapp.scenes.login.LoginScreen
import com.fatihbicgi.ecommerceapp.scenes.login.LoginViewModel
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterContract
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterScreen
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterViewModel
import com.fatihbicgi.ecommerceapp.scenes.splash.SplashScreen

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
            val viewModel = hiltViewModel<LoginViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            LoginScreen(
                uiState = uiState,
                onAction = viewModel::onAction
            )
        }
        composable<ScreenRoutes.RegisterScreen> {//di
            val viewModel = hiltViewModel<RegisterViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            RegisterScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
            )
        }
    }
}