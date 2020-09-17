package com.lifull.android.survey.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lifull.android.survey.data.Row
import com.lifull.android.survey.data.Item
import com.lifull.android.survey.data.Response

/**
 * Abstract ViewModel
 */
abstract class AnswerViewModel<T : Response> : ViewModel() {

    /** Items */
    protected val items: MutableLiveData<List<Item>> = MutableLiveData()
    /** Response */
    protected val response: MutableLiveData<T> = MutableLiveData()

    /**
     * Setter
     *
     * @param rows [List<Row>]
     */
    abstract fun items(rows: List<Row>)

    /**
     * Get a LiveData of items
     *
     * @return [LiveData<List<Item>>]
     */
    fun getItemsLiveData(): LiveData<List<Item>> = items

    /**
     * Get a LiveData of response
     *
     * @return [LiveData<T : Response>]
     */
    fun getResponseLiveData(): LiveData<T> = response
}