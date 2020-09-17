package com.lifull.android.survey.repository

import com.lifull.android.survey.data.local.Questionnaire

/**
 * Interface for acquiring survey data
 */
interface Repository {

    /**
     * Get the [Questionnaire] of the specified ID
     *
     * @param id Questionnaire ID
     */
    fun getQuestionnaire(id: Int): Questionnaire?

    /**
     * Get a list of surveys
     *
     * @return list of surveys
     */
    fun getQuestionnaires(): List<Questionnaire>
}