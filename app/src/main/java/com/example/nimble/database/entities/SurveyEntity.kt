package com.example.nimble.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val SURVEY_DB_TABLE_NAME = "survey"

@Entity(tableName = SURVEY_DB_TABLE_NAME)
data class SurveyEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "description")val description: String,
    @ColumnInfo(name = "cover_image_url")val coverImageUrl: String
)