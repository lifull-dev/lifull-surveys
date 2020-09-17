package com.lifull.android.survey

import android.os.Parcelable
import android.util.SparseArray
import com.lifull.android.survey.data.*
import com.lifull.android.survey.data.local.PageItem
import com.lifull.android.survey.data.local.Questionnaire
import com.lifull.android.survey.ui.QuestionFragment
import kotlinx.android.parcel.Parcelize
import java.lang.IllegalArgumentException

/**
 * Questionnaire Manager
 *
 * @param question Questionnaire
 * @param current current questionnaire
 */
@Parcelize
class QuestionnaireManager(
    val question: Questionnaire,
    private var current: Int = 0
) : Parcelable
{
    /** Response */
    private val responses: SparseArray<Response> = SparseArray()

    /**
     * Get a current page
     *
     * @return [QuestionFragment] or Null
     */
    fun currentPage(): QuestionFragment? {
        return question.pages.getOrNull(current)?.let {
            getPageFragment(getPage(it))
        } ?: run {
            null
        }
    }

    /**
     * Get a next page data
     *
     * @return [PageData] or null
     */
    fun next(): PageData? {
        val page = question.pages.getOrNull(current + 1) ?: return null
        current.inc()
        return getPage(page)
    }

    /**
     * Get a prev page data
     *
     * @return [PageData] or null
     */
    fun back(): PageData? {
        val page = question.pages.getOrNull(current - 1) ?: return null
        current.dec()
        return getPage(page)
    }

    /**
     * Is there a next page
     *
     * @return true / false
     */
    fun hasNext(): Boolean {
        return (question.pages.size - 1) > current
    }

    /**
     * Complete the questionnaire
     */
    fun complete() {
        current = question.pages.size - 1
    }

    /**
     * Save the Response
     */
    fun put(response: Response) {
        responses.put(response.id, response)
    }

    /**
     * Get a list of Response
     *
     * @return list of Response
     */
    fun getResponses(): SparseArray<Response> = responses

    /**
     * Get a [QuestionFragment]
     *
     * @param page [PageData]
     * @return [QuestionFragment]
     */
    fun getPageFragment(page: PageData) = QuestionFragment.newInstance(page, question.configure, hasNext())

    /**
     * Get a Question Page Data
     *
     * @param page Page
     * @return Question Page Data
     */
    private fun getPage(page: PageItem): PageData {
        return page.questionItems.map { questionItem ->
            when (QuestionType.fromId(questionItem.type)) {
                QuestionType.SINGLE_ANSWER -> {
                    questionItem.responseItems.map { item ->
                        RadioButtonRow(
                            item.id,
                            item.label
                        )
                    }.let {
                        SingleAnswerQuestion(
                            questionItem.id,
                            questionItem.body,
                            SingleAnswer(it)
                        )
                    }
                }
                QuestionType.MULTIPLE_ANSWERS -> {
                    questionItem.responseItems.map { item ->
                        CheckBoxRow(
                            item.id,
                            item.label,
                            item.hint
                        )
                    }.let {
                        MultipleAnswersQuestion(
                            questionItem.id,
                            questionItem.body,
                            MultipleAnswers(it)
                        )
                    }
                }
                QuestionType.OPEN_ENDED -> {
                    questionItem.responseItems.map { item ->
                        EditTextRow(
                            item.id,
                            item.hint
                        )
                    }.let {
                        OpenEndedQuestion(
                            questionItem.id,
                            questionItem.body,
                            OpenEnded(it))
                    }
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }.let {
            PageData(page.id, it)
        }
    }
}