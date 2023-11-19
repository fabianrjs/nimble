package com.example.ui_components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun NimbleBackgroundImage(pixelated: Boolean = false) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(
            if (pixelated) R.drawable.background_image_pixeled
            else R.drawable.background_image
        ),
        contentDescription = "Nimble background app image",
        contentScale = ContentScale.Crop
    )
}