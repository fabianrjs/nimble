package com.example.nimble.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.database.repositories.SurveysDatabaseRepository
import com.example.nimble.model.RequestState
import com.example.nimble.model.Survey
import com.example.nimble.network.SessionManager
import com.example.nimble.network.repositories.SurveysRepository
import com.example.nimble.utils.getAuthorization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val surveysRepository: SurveysRepository,
    private val surveysDatabaseRepository: SurveysDatabaseRepository
) : ViewModel() {

    private val _surveys = MutableStateFlow<List<Survey>?>(null)
    val surveys = _surveys.asStateFlow()

    private val _surveysState = MutableStateFlow<RequestState?>(null)
    val surveysState = _surveysState.asStateFlow()

    init {
        getSurveys()
    }

    private fun getSurveys() {
        viewModelScope.launch {
            surveysRepository.getSurveysList(SessionManager.getAccessToken().getAuthorization())
                .flowOn(Dispatchers.IO)
                .onStart { _surveysState.value = RequestState.Loading }
                .catch {
                    handleSurveysRequestError()
                }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.data?.let {
                            _surveysState.value = RequestState.Success
                            _surveys.value = it.map { surveyData ->
                                Survey(
                                    id = surveyData.id,
                                    title = surveyData.attributes.title,
                                    description = surveyData.attributes.description,
                                    coverImageUrl = surveyData.attributes.coverImageUrl
                                )
                            }
                            withContext(Dispatchers.IO) {
                                surveysDatabaseRepository.saveSurveys(_surveys.value!!)
                            }
                        }
                    } else {
                        handleSurveysRequestError()
                    }
                }
        }
    }

    private fun handleSurveysRequestError() {
        _surveysState.value = RequestState.Error
    }
}
