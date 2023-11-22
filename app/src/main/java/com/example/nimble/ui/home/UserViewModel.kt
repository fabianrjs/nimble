package com.example.nimble.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.model.User
import com.example.nimble.model.UserToken
import com.example.nimble.model.getAuthorization
import com.example.nimble.network.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class UserRequestState {
    object Success : UserRequestState()
    object Loading : UserRequestState()
    object Error : UserRequestState()
}

class UserViewModel(
    private val userToken: UserToken,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userInfoState = MutableStateFlow<UserRequestState?>(null)
    val userInfoState = _userInfoState.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo(userToken.getAuthorization())
                .flowOn(Dispatchers.IO)
                .onStart { _userInfoState.value = UserRequestState.Loading }
                .catch { _userInfoState.value = UserRequestState.Error }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.userData?.let {
                            _userInfoState.value = UserRequestState.Success
                            _userInfo.value = User(
                                id = it.id,
                                email = it.attributes.email,
                                name = it.attributes.name,
                                avatarUrl = it.attributes.avatarUrl
                            )
                        }
                    } else {
                        handleLoginRequestError()
                    }
                }
        }
    }

    private fun handleLoginRequestError() {
        _userInfoState.value = UserRequestState.Error
    }
}
