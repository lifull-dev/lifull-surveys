package com.lifull.android.survey.ui.openEnded

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lifull.android.survey.data.EditTextRow
import com.lifull.android.survey.data.MessageRow
import com.lifull.android.survey.data.Row
import com.lifull.android.survey.data.EditTextItem
import com.lifull.android.survey.data.QuestionItem
import com.lifull.android.survey.data.OpenEndedResponse
import com.lifull.android.survey.ui.common.AnswerViewModel
import java.lang.IllegalStateException

/**
 * [ViewModel] for OpenEnded
 *
 * @param questionnaireId Questionnaire ID
 */
class OpenEndedViewModel(
    private val questionnaireId: Int
) : AnswerViewModel<OpenEndedResponse>()
{
    /** Callback */
    val callback = object : OpenEndedCallback {
        override fun afterTextChanged(id: Int, editable: Editable?) {
            editable?.toString().let { text ->
                if (text.isNullOrEmpty()) {
                    response.postValue(null)
                } else {
                    response.postValue(OpenEndedResponse(id, text))
                }
            }
        }
    }

    /**
     * @inheritDoc
     */
    override fun items(rows: List<Row>) {
        rows.map {
            return@map when (it) {
                is EditTextRow -> {
                    EditTextItem(it.id, it.hint)
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
     * [Factory] for [OpenEndedViewModel]
     *
     * @param questionnaireId 質問ID
     */
    class Factory(
        private val questionnaireId: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OpenEndedViewModel(
                questionnaireId
            ) as T
        }
    }
}