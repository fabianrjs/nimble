package com.example.nimble.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
fun LogInScreen(
    authViewModel: AuthViewModel
) {
    val emailValue = authViewModel.emailValue.collectAsState()
    val passwordValue = authViewModel.passwordValue.collectAsState()
    val loginRequestState = authViewModel.loginRequestState.collectAsState()



    Box(modifier = Modifier.fillMaxSize()) {
        NimbleBackgroundImage(pixelated = true)
        LogInForm(
            emailValue = emailValue,
            passwordValue = passwordValue,
            loginRequestState = loginRequestState,
            onEmailValueChange = authViewModel::onEmailValueChange,
            onPasswordValueChange = authViewModel::onPasswordValueChange,
            onLoginButtonClick = authViewModel::login,
        )
    }
}

@Composable
private fun LogInForm(
    emailValue: State<String>,
    passwordValue: State<String>,
    loginRequestState: State<LoginRequestState?>,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (nimbleLogo, emailInput, passwordInput, logInButton, errorMessage) = createRefs()
        val isRequestLoading = loginRequestState.value is LoginRequestState.Loading
        val formEnabled = isRequestLoading.not()

        Image(
            modifier = Modifier
                .constrainAs(nimbleLogo) {
                    top.linkTo(parent.top, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(vertical = 109.dp),
            painter = painterResource(id = R.drawable.nimble_logo),
            contentDescription = "Nimble Logo"
        )

        InputTextField(
            modifier = Modifier
                .constrainAs(emailInput) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(nimbleLogo.bottom)
                }
                .padding(horizontal = 24.dp),
            value = emailValue,
            onValueChange = onEmailValueChange,
            placeholderText = "Email",
            keyboardType = KeyboardType.Email,
            enabled = formEnabled
        )

        InputTextField(
            modifier = Modifier
                .constrainAs(passwordInput) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(emailInput.bottom)
                }
                .padding(horizontal = 24.dp, vertical = 20.dp),
            value = passwordValue,
            onValueChange = onPasswordValueChange,
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
            },
            enabled = formEnabled
        )

        NimbleButton(
            modifier = Modifier
                .constrainAs(logInButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(passwordInput.bottom)
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            textButton = "Log In",
            onClick = onLoginButtonClick,
            isLoading = isRequestLoading
        )

        if (loginRequestState.value is LoginRequestState.Error) {
            Text(
                modifier = Modifier.constrainAs(errorMessage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(logInButton.bottom, 20.dp)
                },
                text = "An error occurred. Try again",
                color = Color.Red
            )
        }
    }
}
