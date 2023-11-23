package com.example.nimble.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nimble.database.entities.SURVEY_DB_TABLE_NAME
import com.example.nimble.database.entities.SurveyEntity

@Dao
interface SurveyDao {

    @Query("""
        SELECT *
        FROM $SURVEY_DB_TABLE_NAME
    """)
    fun getSurveys(): List<SurveyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSurveys(surveys: List<SurveyEntity>)
}
