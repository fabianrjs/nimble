package com.example.nimble.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.model.UserToken
import com.example.nimble.network.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class LoginRequestState {
    class Success(val userToken: UserToken) : LoginRequestState()
    object Loading : LoginRequestState()
    object Error : LoginRequestState()
}

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _emailValue = MutableStateFlow("")
    val emailValue = _emailValue.asStateFlow()

    private val _passwordValue = MutableStateFlow("")
    val passwordValue = _passwordValue.asStateFlow()

    private val _loginRequestState = MutableStateFlow<LoginRequestState?>(null)
    val loginRequestState = _loginRequestState.asStateFlow()

    fun onEmailValueChange(newValue: String) {
        _emailValue.value = newValue
    }

    fun onPasswordValueChange(newValue: String) {
        _passwordValue.value = newValue
    }

    fun login() {
        viewModelScope.launch {
            authRepository.login(_emailValue.value, _passwordValue.value)
                .flowOn(Dispatchers.IO)
                .onStart { _loginRequestState.value = LoginRequestState.Loading }
                .catch { handleLoginRequestError() }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.data?.let {
                            _loginRequestState.value = LoginRequestState.Success(UserToken(
                                id = it.id,
                                accessToken = it.attributes.accessToken,
                                tokenType = it.attributes.tokenType,
                                expiresIn = it.attributes.expiresIn,
                                refreshToken = it.attributes.refreshToken,
                                createdAt = it.attributes.createdAt
                            ))
                        }
                    } else {
                        handleLoginRequestError()
                    }
                }
        }
    }

    private fun handleLoginRequestError() {
        _loginRequestState.value = LoginRequestState.Error
    }

}
