package com.lifull.android.survey.ui

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lifull.android.survey.data.Response
import com.lifull.android.survey.ui.common.AnswerViewModel
import java.util.*

/**
 * [ViewModel] for [QuestionFragment]
 */
class QuestionFragmentViewModel : ViewModel() {

    /** [ViewModel] for Child Fragments */
    val viewModels: SparseArray<AnswerViewModel<*>> = SparseArray()
    /** [UUID] */
    val uuid: UUID = UUID.randomUUID()
    /** Responses */
    val responses = SparseArray<Response>()

    /**
     * [Factory] for [QuestionFragmentViewModel]
     */
    class Factory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return QuestionFragmentViewModel() as T
        }
    }
}