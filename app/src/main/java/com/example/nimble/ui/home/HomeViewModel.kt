package com.example.nimble.ui.home

import androidx.lifecycle.ViewModel
import com.example.nimble.model.Survey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _surveys = MutableStateFlow<List<Survey>?>(null)
    val surveys = _surveys.asStateFlow()

    init {
        _surveys.value = listOf(
            Survey(
                id = "123",
                title = "Survey 1",
                description = "Description if survey 1",
                coverImageUrl = "https://firebasestorage.googleapis.com/v0/b/reproductor-mp3-9962d.appspot.com/o/Background.png?alt=media&token=a2c6c046-e628-4d2c-9467-6a13ee19d14e"
            ),
            Survey(
                id = "456",
                title = "Survey 2",
                description = "Description if survey 2",
                coverImageUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/287db81c5e4242412cc0_"
            ),
            Survey(
                id = "789",
                title = "Survey 3",
                description = "Description if survey 3",
                coverImageUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/287db81c5e4242412cc0_"
            )
        )
    }
}
