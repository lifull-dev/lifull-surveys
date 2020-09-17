package com.lifull.android.survey.ui.openEnded

import android.text.Editable

/**
 * Callback for OpenEnded
 */
interface OpenEndedCallback {

    /**
     * Called when text changed
     *
     * @param id ID
     * @param editable input text
     */
    fun afterTextChanged(id: Int, editable: Editable?)
}