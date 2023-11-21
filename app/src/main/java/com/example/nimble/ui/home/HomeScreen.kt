package com.example.nimble.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    userViewModel: UserViewModel
) {
    Text(text = "Home Screen")
}
