package com.example.nimble.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.model.Survey
import com.example.nimble.model.UserToken
import com.example.nimble.model.getAuthorization
import com.example.nimble.network.repositories.SurveysRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class SurveysRequestState {
    object Success : SurveysRequestState()
    object Loading : SurveysRequestState()
    object Error : SurveysRequestState()
}

class HomeViewModel(
    private val surveysRepository: SurveysRepository
) : ViewModel() {

    private val _surveys = MutableStateFlow<List<Survey>?>(null)
    val surveys = _surveys.asStateFlow()

    private val _surveysState = MutableStateFlow<SurveysRequestState?>(null)
    val surveysState = _surveysState.asStateFlow()

    fun getSurveys(userAuthToken: UserToken) {
        viewModelScope.launch {
            surveysRepository.getSurveysList(userAuthToken.getAuthorization())
                .flowOn(Dispatchers.IO)
                .onStart { _surveysState.value = SurveysRequestState.Loading }
                .catch { handleSurveysRequestError() }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.data?.let {
                            _surveysState.value = SurveysRequestState.Success
                            _surveys.value = it.map { surveyData ->
                                Survey(
                                    id = surveyData.id,
                                    title = surveyData.attributes.title,
                                    description = surveyData.attributes.description,
                                    coverImageUrl = surveyData.attributes.coverImageUrl
                                )
                            }
                        }
                    } else {
                        handleSurveysRequestError()
                    }
                }
        }
    }

    private fun handleSurveysRequestError() {
        _surveysState.value = SurveysRequestState.Error
    }
}
