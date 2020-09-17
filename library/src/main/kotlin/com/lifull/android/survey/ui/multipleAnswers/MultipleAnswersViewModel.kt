package com.lifull.android.survey.ui.multipleAnswers

import android.text.Editable
import android.util.SparseBooleanArray
import androidx.core.util.forEach
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lifull.android.survey.data.MessageRow
import com.lifull.android.survey.data.CheckBoxRow
import com.lifull.android.survey.data.Row
import com.lifull.android.survey.data.*
import com.lifull.android.survey.ui.common.AnswerViewModel
import java.lang.IllegalStateException

/**
 * [ViewModel] for MultipleAnswer
 *
 * @param questionnaireId Questionnaire ID
 */
class MultipleAnswersViewModel(
    private val questionnaireId: Int
) : AnswerViewModel<MultipleAnswersResponse>()
{
    /** check status */
    private val checkStatus = SparseBooleanArray()
    /** input string */
    private var inputText: String? = null

    /** Callback */
    val callback = object : MultipleAnswersCallback {
        override fun onCheckedChanged(id: Int, isChecked: Boolean, position: Int) {
            checkStatus.put(id, isChecked)

            val list = ArrayList<Int>()
            checkStatus.forEach { key, value ->
                if (value) {
                    list.add(key)
                }
            }

            if (list.isEmpty()) {
                response.postValue(null)
            } else {
                response.postValue(MultipleAnswersResponse(questionnaireId, list))
            }
        }

        override fun afterTextChanged(id: Int, editable: Editable?) {
            if (id != CheckBoxItem.OTHER_RESPONSE_ID) {
                return
            }

            inputText = editable?.toString() ?: ""

            val list = ArrayList<Int>()
            checkStatus.forEach { key, value ->
                if (value) {
                    list.add(key)
                }
            }

            if (list.isEmpty()) {
                response.postValue(null)
            } else {
                response.postValue(MultipleAnswersResponse(questionnaireId, list, inputText))
            }
        }
    }

    /**
     * @inheritDoc
     */
    override fun items(rows: List<Row>) {
        rows.map {
            return@map when (it) {
                is CheckBoxRow -> {
                    if (it.id == CheckBoxItem.OTHER_RESPONSE_ID) {
                        CheckBoxWithEditTextItem(it.id, it.label, it.hint)
                    } else {
                        CheckBoxItem(it.id, it.label)
                    }
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
     * [Factory] for [MultipleAnswersViewModel]
     *
     * @param questionnaireId
     */
    class Factory(
        private val questionnaireId: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MultipleAnswersViewModel(
                questionnaireId
            ) as T
        }
    }
}