package com.example.nimble

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nimble.ui.login.LogInScreen
import com.example.nimble.ui.SplashScreen
import com.example.nimble.ui.home.HomeScreen
import com.example.nimble.ui.login.AuthViewModel
import com.example.nimble.ui.login.LogOutScreen
import com.example.nimble.ui.survey.SurveyDetailScreen
import com.example.nimble.utils.getAuthorization
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

sealed class NimbleRoutes(val route: String) {
    object SplashScreen : NimbleRoutes("SplashScreen")
    object LogInScreen : NimbleRoutes("LogInScreen")
    object HomeScreen : NimbleRoutes("HomeScreen")
    object SurveyDetailScreen : NimbleRoutes("SurveyDetailScreen")
    object LogOutScreen : NimbleRoutes("LogOutScreen")
}

@Composable
fun NimbleNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel(),
) {
    val accessToken = authViewModel.accessToken.collectAsState()

    LaunchedEffect(accessToken.value) {
        if (accessToken.value.isNullOrEmpty().not()) {
            if (navController.currentDestination?.route == NimbleRoutes.LogInScreen.route) {
                navController.popBackStack(NimbleRoutes.LogInScreen.route, true)
            }
            else {
                navController.popBackStack(NimbleRoutes.SplashScreen.route, true)
            }
            navController.navigate(NimbleRoutes.HomeScreen.route)
        }
        else if (navController.currentDestination?.route == NimbleRoutes.LogOutScreen.route) {
            navController.popBackStack(NimbleRoutes.HomeScreen.route, true)
            navController.navigate(NimbleRoutes.LogInScreen.route)
        }
    }

    NavHost(navController = navController, startDestination = NimbleRoutes.SplashScreen.route) {
        composable(route = NimbleRoutes.SplashScreen.route) {
            SplashScreen {
                navController.popBackStack(NimbleRoutes.SplashScreen.route, true)
                navController.navigate(NimbleRoutes.LogInScreen.route)
            }
        }
        composable(route = NimbleRoutes.LogInScreen.route) { LogInScreen(authViewModel) }
        composable(route = NimbleRoutes.HomeScreen.route) {
            HomeScreen(
                userViewModel = koinViewModel(parameters = {
                    parametersOf(accessToken.value?.getAuthorization() ?: "")
                }),
                homeViewModel = koinViewModel(parameters = {
                    parametersOf(accessToken.value?.getAuthorization() ?: "")
                }),
                navController = navController
            )
        }
        composable(route = NimbleRoutes.SurveyDetailScreen.route) { SurveyDetailScreen() }
        composable(route = NimbleRoutes.LogOutScreen.route) { LogOutScreen(authViewModel) }
    }
}
