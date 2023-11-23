package com.example.nimble.ui.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.nimble.R
import com.example.nimble.model.RequestState
import com.example.nimble.ui.login.AuthViewModel
import com.example.ui_components.InputTextField
import com.example.ui_components.NimbleBackgroundImage
import com.example.ui_components.NimbleButton

@Composable
fun ResetPasswordScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val emailValue = authViewModel.emailResetPasswordValue.collectAsState()
    val resetPasswordState = authViewModel.resetPasswordRequestState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        NimbleBackgroundImage(pixelated = true)
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            val (backButton, nimbleLogo, instructions, emailInput, resetButton, resultMessage) = createRefs()

            Icon(
                modifier = Modifier
                    .constrainAs(backButton) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, 30.dp)
                    }
                    .clickable { navController.navigateUp() },
                painter = painterResource(id = R.drawable.arrow_back_ic),
                contentDescription = "Go back button",
                tint = Color.White
            )

            Image(
                modifier = Modifier
                    .constrainAs(nimbleLogo) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(backButton.bottom, 66.dp)
                    },
                painter = painterResource(id = R.drawable.nimble_logo),
                contentDescription = "Nimble Logo"
            )

            Text(
                modifier = Modifier
                    .constrainAs(instructions) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(nimbleLogo.bottom, 24.dp)
                    },
                text = stringResource(R.string.instructions_reset_password_screen),
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                color = Color.White
            )

            InputTextField(
                modifier = Modifier
                    .constrainAs(emailInput) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(instructions.bottom, 96.dp)
                    },
                value = emailValue,
                onValueChange = authViewModel::onEmailResetPasswordValueChange,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                placeholderText = "Email"
            )

            NimbleButton(
                modifier = Modifier
                    .constrainAs(resetButton) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(emailInput.bottom, 20.dp)
                    }
                    .fillMaxWidth()
                    .height(56.dp),
                textButton = "Reset",
                onClick = authViewModel::resetPassword,
                isLoading = resetPasswordState.value is RequestState.Loading
            )

            when(resetPasswordState.value) {
                is RequestState.SuccessWithMessage -> {
                    Text(
                        modifier = Modifier
                            .constrainAs(resultMessage) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(resetButton.bottom, 30.dp)
                            },
                        text = (resetPasswordState.value as RequestState.SuccessWithMessage).message,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
                is RequestState.Error -> {
                    Text(
                        modifier = Modifier
                            .constrainAs(resultMessage) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(resetButton.bottom, 30.dp)
                            },
                        text = "An error has occurred, try gain",
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
                else -> Unit
            }

        }
    }
}
