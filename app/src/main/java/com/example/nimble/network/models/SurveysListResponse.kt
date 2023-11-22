package com.example.nimble.network.models

import com.google.gson.annotations.SerializedName

data class SurveysListResponse(
    @SerializedName("data") val data: List<SurveyData>,
    @SerializedName("meta") val meta: Meta
)

data class SurveyData(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: SurveyAttributes,
    @SerializedName("relationships") val relationships: Relationships
)

data class SurveyAttributes(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thank_email_above_threshold") val thankEmailAboveThreshold: String,
    @SerializedName("thank_email_below_threshold") val thankEmailBelowThreshold: String,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("cover_image_url") val coverImageUrl: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("active_at") val activeAt: String,
    @SerializedName("inactive_at") val inactiveAt: String?,
    @SerializedName("survey_type") val surveyType: String
)

data class Relationships(
    @SerializedName("questions") val questions: Questions
)

data class Questions(
    @SerializedName("data") val data: List<Question>
)

data class Question(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String
)

data class Meta(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("page_size") val pageSize: Int,
    @SerializedName("records") val records: Int
)
