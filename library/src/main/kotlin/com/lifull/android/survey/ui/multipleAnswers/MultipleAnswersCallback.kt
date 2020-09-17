package com.lifull.android.survey.ui.multipleAnswers

import android.text.Editable

/**
 * Callback for MultipleAnswers
 */
interface MultipleAnswersCallback {

    /**
     * Called when check changed
     *
     * @param id ID
     * @param isChecked check status
     * @param position posiiton
     */
    fun onCheckedChanged(id: Int, isChecked: Boolean, position: Int)

    /**
     * Called when text changed
     *
     * @param id ID
     * @param editable input text
     */
    fun afterTextChanged(id: Int, editable: Editable?)
}