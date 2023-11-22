package com.example.nimble

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nimble.model.getAuthorization
import com.example.nimble.ui.login.LogInScreen
import com.example.nimble.ui.SplashScreen
import com.example.nimble.ui.home.HomeScreen
import com.example.nimble.ui.login.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

sealed class NimbleRoutes(val route: String) {
    object SplashScreenRoute : NimbleRoutes("SplashScreen")
    object LogInScreenRoute : NimbleRoutes("LogInScreen")
    object HomeScreen : NimbleRoutes("HomeScreen")
}

@Composable
fun NimbleNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel(),
) {
    val userToken = authViewModel.userToken.collectAsState()
    val accessToken = authViewModel.accessToken.collectAsState()

    LaunchedEffect(accessToken.value) {
        if (accessToken.value.isNullOrEmpty().not()) {
            navController.popBackStack(NimbleRoutes.SplashScreenRoute.route, true)
            navController.navigate(NimbleRoutes.HomeScreen.route)
        }
    }

    NavHost(navController = navController, startDestination = NimbleRoutes.SplashScreenRoute.route) {
        composable(route = NimbleRoutes.SplashScreenRoute.route) {
            SplashScreen {
                navController.popBackStack(NimbleRoutes.SplashScreenRoute.route, true)
                navController.navigate(NimbleRoutes.LogInScreenRoute.route)
            }
        }
        composable(route = NimbleRoutes.LogInScreenRoute.route) { LogInScreen(authViewModel) }
        composable(route = NimbleRoutes.HomeScreen.route) {
            HomeScreen(
                userViewModel = koinViewModel(parameters = {
                    parametersOf(userToken.value?.getAuthorization() ?: "")
                }),
                homeViewModel = koinViewModel(parameters = {
                    parametersOf(userToken.value?.getAuthorization() ?: "")
                }),
            )
        }
    }
}
