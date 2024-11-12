package com.fatihbicgi.ecommerceapp.scenes.navigation

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.fatihbicgi.ecommerceapp.scenes.login.LoginScreen
import com.fatihbicgi.ecommerceapp.scenes.login.LoginViewModel
import com.fatihbicgi.ecommerceapp.scenes.products.ProductDetailScreen
import com.fatihbicgi.ecommerceapp.scenes.products.ProductDetailViewModel
import com.fatihbicgi.ecommerceapp.scenes.products.ProductListScreen
import com.fatihbicgi.ecommerceapp.scenes.products.ProductListViewModel
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterContract
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterScreen
import com.fatihbicgi.ecommerceapp.scenes.register.RegisterViewModel
import com.fatihbicgi.ecommerceapp.scenes.splash.SplashScreen
import com.fatihbicgi.ecommerceapp.scenes.userdetail.UserDetailScreen
import com.fatihbicgi.ecommerceapp.scenes.userdetail.UserDetailViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    sharedPref: SharedPreferences
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenRoutes.ProductListScreen //burada da stardest karar verilebilir
    ) {

        composable<ScreenRoutes.SplashScreen> {
            SplashScreen(
                onGoToLoginScreen = {
                    navController.navigate(ScreenRoutes.LoginScreen)
                },
                onGoToRegisterScreen = {
                    navController.navigate(ScreenRoutes.RegisterScreen)
                },
                onGoToUserDetailScreen = { userId ->
                    navController.navigate(ScreenRoutes.UserDetailScreen(id = userId)) {
                        popUpTo(ScreenRoutes.SplashScreen) { inclusive = true }
                    }
                },
                sharedPref = sharedPref,
            )
        }
        composable<ScreenRoutes.LoginScreen> {
            val viewModel = hiltViewModel<LoginViewModel>()
            val uiEffect = viewModel.uiEffect
            val uiState by viewModel.uiState.collectAsState()
            val userId by viewModel.userId.collectAsState(initial = "")
            LoginScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onGoToUserDetailScreen = {
                    if (uiState.isLoginSuccessfuly) {
                        navController.navigate(
                            ScreenRoutes.UserDetailScreen(
                                id = userId,
                            )
                        )
                        {
                            popUpTo(ScreenRoutes.SplashScreen) { inclusive = true }
                        }
                        Log.i("if register state", uiState.isLoginSuccessfuly.toString())
                        Log.i("if ScreenRoutes userId", userId)
                    }
                    //2. kez tıklandığından gidiyor, önce true yapıyor sonra true olduğu için gidiyor
                    Log.i("register state", uiState.isLoginSuccessfuly.toString())
                },

                )
        }
        composable<ScreenRoutes.RegisterScreen> {
            val viewModel = hiltViewModel<RegisterViewModel>()
            val uiEffect = viewModel.uiEffect
            val uiState by viewModel.uiState.collectAsState()
            val userId by viewModel.userId.collectAsState(initial = "")
            RegisterScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onGoToUserDetailScreen = {
                    if (uiState.isRegisteredSuccessfuly) {
                        navController.navigate(
                            ScreenRoutes.UserDetailScreen(
                                id = userId,
                            )
                        )
                        {
                            popUpTo(ScreenRoutes.SplashScreen) { inclusive = true }
                        }
                        Log.i("if register state", uiState.isRegisteredSuccessfuly.toString())
                        Log.i("if ScreenRoutes userId", userId)
                    }
                    //2. kez tıklandığından gidiyor, önce true yapıyor sonra true olduğu için gidiyor
                    Log.i("register state", uiState.isRegisteredSuccessfuly.toString())
                },

                )
        }
        //injectable
        composable<ScreenRoutes.UserDetailScreen> { backStackEntry ->
            val viewModel = hiltViewModel<UserDetailViewModel>(backStackEntry)
            val uiState by viewModel.uiState.collectAsState()
            val uiEffect = viewModel.uiEffect
            UserDetailScreen(
                viewModel = viewModel,
                onLogout = {
                    navController.navigate(ScreenRoutes.SplashScreen) {
                        popUpTo(ScreenRoutes.SplashScreen) { inclusive = true }
                    }
                },
                uiState = uiState,
                uiEffect = uiEffect,
            )
        }
        composable<ScreenRoutes.ProductListScreen> {
            val viewModel = hiltViewModel<ProductListViewModel>()
            val uiEffect = viewModel.uiEffect
            val uiState by viewModel.uiState.collectAsState()
            ProductListScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiAction = viewModel::onAction,
                onNavigateToDetail = {
                    navController.navigate(ScreenRoutes.ProductDetailScreen(id = it))
                }
            )
        }
        composable<ScreenRoutes.ProductDetailScreen> { backStackEntry ->
            val viewModel = hiltViewModel<ProductDetailViewModel>(backStackEntry)
            val uiState by viewModel.uiState.collectAsState()
            ProductDetailScreen(
                uiState = uiState,
            )
        }
    }

}

