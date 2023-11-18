package com.example.nimble

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nimble.ui.LogInScreen
import com.example.nimble.ui.SplashScreen

sealed class NimbleRoutes(val route: String) {
    object SplashScreenRoute : NimbleRoutes("SplashScreen")
    object LogInScreenRoute : NimbleRoutes("LogInScreen")
}

@Composable
fun NimbleNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = NimbleRoutes.SplashScreenRoute.route) {
        composable(route = NimbleRoutes.SplashScreenRoute.route) {
            SplashScreen {
                navController.navigate(NimbleRoutes.LogInScreenRoute.route)
            }
        }
        composable(route = NimbleRoutes.LogInScreenRoute.route) { LogInScreen() }
    }

}
