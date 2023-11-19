package com.example.nimble.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.example.nimble.R
import kotlinx.coroutines.delay

const val SPLASH_SCREEN_VISIBLE_TIME = 1000L
const val NIMBLE_LOGO_ANIMATION_TIME = 900

@Composable
fun SplashScreen(
    navigateToNextActivity: () -> Unit
) {
    val logoAlpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(true) {
        logoAlpha.animateTo(1f, animationSpec = tween(NIMBLE_LOGO_ANIMATION_TIME))
        delay(SPLASH_SCREEN_VISIBLE_TIME)
        navigateToNextActivity()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.alpha(logoAlpha.value),
            painter = painterResource(id = R.drawable.nimble_logo),
            contentDescription = "Nimble Logo"
        )
    }
}
