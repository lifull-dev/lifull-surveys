package com.lifull.android.survey.ui.singleAnswer

/**
 * Callback for SingleAnswer
 */
interface SingleAnswerCallback {

    /**
     * Called when check change
     *
     * @param id ID
     * @param isChecked check status
     * @param prevPosition prev checked position
     * @param currentPosition current checked position
     */
    fun onCheckedChanged(id: Int, isChecked: Boolean, prevPosition: Int, currentPosition: Int)
}