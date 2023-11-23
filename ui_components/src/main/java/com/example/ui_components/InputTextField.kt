package com.example.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui_components.theme.neuzeitDBook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    modifier: Modifier,
    value: State<String>,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType,
    enabled: Boolean = true,
    trailingIcon: @Composable () -> Unit = {}
) {

    val transparentGrayColor = Color.White.copy(alpha = 0.15f)

    OutlinedTextField(
        value = value.value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        textStyle = TextStyle(fontSize = 17.sp),
        placeholder = {
            Text(
                text = placeholderText,
                fontFamily = neuzeitDBook,
                fontSize = 17.sp
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            containerColor = transparentGrayColor,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color.White.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(size = 15.dp),
        trailingIcon = trailingIcon,
        visualTransformation =
            if (keyboardType == KeyboardType.Password) PasswordVisualTransformation()
            else VisualTransformation.None,
        singleLine = true,
        enabled = enabled
    )
}

@Preview
@Composable
private fun InputFieldPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        InputTextField(
            modifier = Modifier,
            value = remember { mutableStateOf("") },
            onValueChange = {},
            placeholderText = "Email",
            keyboardType = KeyboardType.Email,
        )
    }
}
