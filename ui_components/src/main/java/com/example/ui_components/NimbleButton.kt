package com.example.ui_components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NimbleButton(
    modifier: Modifier,
    textButton: String,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF15151A),
            disabledContainerColor = Color.White.copy(alpha = 0.8f),
            disabledContentColor = Color(0xFF15151A)
        ),
        shape = RoundedCornerShape(size = 15.dp),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = Color(0xFF15151A)
            )
        } else {
            Text(
                text = textButton,
                fontSize = 17.sp,
            )
        }
    }
}
