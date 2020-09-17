package com.lifull.android.survey.ui.singleAnswer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lifull.android.survey.data.MessageRow
import com.lifull.android.survey.data.RadioButtonRow
import com.lifull.android.survey.data.Row
import com.lifull.android.survey.data.QuestionItem
import com.lifull.android.survey.data.RadioButtonItem
import com.lifull.android.survey.data.SingleAnswerResponse
import com.lifull.android.survey.ui.common.AnswerViewModel
import java.lang.IllegalStateException

/**
 * [ViewModel] for SingleAnswer
 *
 * @param questionnaireId Questionnaire ID
 */
class SingleAnswerViewModel(
    private val questionnaireId: Int
) : AnswerViewModel<SingleAnswerResponse>()
{
    /** Callback */
    val callback = object : SingleAnswerCallback {
        override fun onCheckedChanged(
            id: Int,
            isChecked: Boolean,
            prevPosition: Int,
            currentPosition: Int
        ) {
            if (isChecked) {
                response.postValue(SingleAnswerResponse(questionnaireId, id))
            } else {
                response.postValue(null)
            }
        }
    }

    /**
     * @inheritDoc
     */
    override fun items(rows: List<Row>) {
        rows.map {
            return@map when (it) {
                is RadioButtonRow -> {
                    RadioButtonItem(it.id, it.label)
                }
                is MessageRow -> {
                    QuestionItem(it.id, it.message)
                }
                else -> {
                    throw IllegalStateException()
                }
            }
        }.let {
            items.postValue(it)
        }
    }

    /**
     * [Factory] for [SingleAnswerViewModel]
     *
     * @param questionnaireId 質問ID
     */
    class Factory(
        private val questionnaireId: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SingleAnswerViewModel(
                questionnaireId
            ) as T
        }
    }
}