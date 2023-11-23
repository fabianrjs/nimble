package com.example.nimble.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.database.repositories.AuthDatabaseRepository
import com.example.nimble.datastore.AuthStorageRepository
import com.example.nimble.model.RequestState
import com.example.nimble.model.UserToken
import com.example.nimble.network.SessionManager
import com.example.nimble.network.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val authStorageRepository: AuthStorageRepository,
    private val authDatabaseRepository: AuthDatabaseRepository,
) : ViewModel() {

    private val _emailValue = MutableStateFlow("")
    val emailValue = _emailValue.asStateFlow()

    private val _passwordValue = MutableStateFlow("")
    val passwordValue = _passwordValue.asStateFlow()

    private val _emailResetPasswordValue = MutableStateFlow("")
    val emailResetPasswordValue = _emailResetPasswordValue.asStateFlow()

    private val _loginRequestState = MutableStateFlow<RequestState?>(null)
    val loginRequestState = _loginRequestState.asStateFlow()

    private val _resetPasswordRequestState = MutableStateFlow<RequestState?>(null)
    val resetPasswordRequestState = _resetPasswordRequestState.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    init {
        getUserAccessToken()
    }

    private fun getUserAccessToken() {
        viewModelScope.launch {
            authStorageRepository.getAccessToken()
                .flowOn(Dispatchers.IO)
                .collect {
                    if (it.isNullOrEmpty().not()) {
                        authDatabaseRepository.getUserToken(it!!)
                            .flowOn(Dispatchers.IO)
                            .collect { userTokenData ->
                                SessionManager.setUserToken(userTokenData)
                                _accessToken.value = it
                            }
                    }
                }
        }
    }

    fun onEmailValueChange(newValue: String) {
        _emailValue.value = newValue
    }

    fun onPasswordValueChange(newValue: String) {
        _passwordValue.value = newValue
    }

    fun onEmailResetPasswordValueChange(newValue: String) {
        _emailResetPasswordValue.value = newValue
    }

    fun login() {
        viewModelScope.launch {
            authRepository.login(_emailValue.value, _passwordValue.value)
                .flowOn(Dispatchers.IO)
                .onStart { _loginRequestState.value = RequestState.Loading }
                .catch { handleLoginRequestError() }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.data?.let {
                            _loginRequestState.value = RequestState.Success
                            _accessToken.value = it.attributes.accessToken
                            SessionManager.setUserToken(UserToken(
                                id = it.id,
                                accessToken = it.attributes.accessToken,
                                tokenType = it.attributes.tokenType,
                                expiresIn = it.attributes.expiresIn,
                                refreshToken = it.attributes.refreshToken,
                                createdAt = it.attributes.createdAt
                            ))
                            withContext(Dispatchers.IO) {
                                authStorageRepository.saveUserAccessToken(it.attributes.accessToken)
                                authDatabaseRepository.saveUserToken(SessionManager.getUserToken()!!)
                            }
                        }
                    } else {
                        handleLoginRequestError()
                    }
                }
        }
    }

    private fun handleLoginRequestError() {
        _loginRequestState.value = RequestState.Error
    }

    private fun handleResetPasswordRequestError() {
        _resetPasswordRequestState.value = RequestState.Error
    }

    fun logout() {
        SessionManager.getUserToken()?.let { userToken ->
            viewModelScope.launch {
                authRepository.logout(userToken.accessToken)
                    .onEach {
                        if (it.isSuccessful) {
                            authStorageRepository.deleteUserAccessToken()
                            authDatabaseRepository.deleteUserToken(userToken)
                        }
                    }
                    .flowOn(Dispatchers.IO)
                    .collect {
                        if (it.isSuccessful) {
                            clearVariables()
                        }
                    }
            }
        }
    }

    private fun clearVariables() {
        SessionManager.setUserToken(null)
        _accessToken.value = null
        _loginRequestState.value = null
        _emailValue.value = ""
        _passwordValue.value = ""
    }

    fun resetPassword() {
        viewModelScope.launch {
            authRepository.resetPassword(_emailResetPasswordValue.value)
                .flowOn(Dispatchers.IO)
                .onStart { _resetPasswordRequestState.value = RequestState.Loading }
                .catch { handleResetPasswordRequestError() }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.meta?.message?.let { message ->
                            _resetPasswordRequestState.value = RequestState.SuccessWithMessage(message)
                        }
                    } else {
                        handleResetPasswordRequestError()
                    }
                }
        }
    }

}
