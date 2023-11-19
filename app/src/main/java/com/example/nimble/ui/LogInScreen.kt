package com.example.nimble.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.nimble.R
import com.example.ui_components.InputTextField
import com.example.ui_components.NimbleBackgroundImage
import com.example.ui_components.NimbleButton

@Composable
fun LogInScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        NimbleBackgroundImage(pixelated = true)
        LogInForm()
    }
}

@Composable
private fun LogInForm() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (nimbleLogo, emailInput, passwordInput, logInButton) = createRefs()
        val emailValue = remember { mutableStateOf("") }
        val passwordValue = remember { mutableStateOf("") }

        Image(
            modifier = Modifier
                .constrainAs(nimbleLogo) {
                    top.linkTo(parent.top, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(vertical = 109.dp),
            painter = painterResource(id = R.drawable.nimble_logo),
            contentDescription = "Nimble Logo"
        )

        InputTextField(
            modifier = Modifier
                .constrainAs(emailInput) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(nimbleLogo.bottom)
                }.padding(horizontal = 24.dp),
            value = emailValue,
            onValueChange = { newValue ->
                emailValue.value = newValue
            },
            placeholderText = "Email",
            keyboardType = KeyboardType.Email,
        )

        InputTextField(
            modifier = Modifier
                .constrainAs(passwordInput) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(emailInput.bottom)
                }.padding(horizontal = 24.dp, vertical = 20.dp),
            value = passwordValue,
            onValueChange = { newValue ->
                passwordValue.value = newValue
            },
            placeholderText = "Password",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            trailingIcon = {
                Text(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { /* TODO */ },
                    text = "Forgot?",
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        )

        NimbleButton(
            modifier = Modifier
                .constrainAs(logInButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(passwordInput.bottom)
                }.fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            textButton = "Log In",
            onClick = { /* TODO */ }
        )
    }
}
