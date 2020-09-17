package com.lifull.android.survey.data

import android.os.Parcelable
import com.lifull.android.survey.QuestionType
import kotlinx.android.parcel.Parcelize

/**
 * Response
 */
sealed class Response: Parcelable
{
    /** Question ID */
    abstract val id: Int
    /** Question Type */
    abstract val type: Int

    /**
     * Validation for Response
     *
     * @return true: success/ false: failed
     */
    abstract fun isValid(): Boolean
}

/**
 * Check Complete a Questions
 *
 * @return true:Completed / false: none
 */
fun Response?.isComplete(): Boolean {
    return this == null || !this.isValid()
}

/**
 * Single Answer
 *
 * @param id Question ID
 * @param selected selected choice ID
 */
@Parcelize
data class SingleAnswerResponse(
    override val id: Int,
    val selected: Int
): Response()
{
    override val type: Int
        get() = QuestionType.SINGLE_ANSWER.id

    /**
     * @inheritDoc
     */
    override fun isValid(): Boolean {
        return true
    }
}

/**
 * Multiple Answers
 *
 * @param id Question ID
 * @param selected selected choice ID
 * @param inputText input text
 */
@Parcelize
data class MultipleAnswersResponse(
    override val id: Int,
    val selected: List<Int>,
    val inputText: String? = null
): Response()
{
    override val type: Int
        get() = QuestionType.MULTIPLE_ANSWERS.id

    /**
     * @inheritDoc
     */
    override fun isValid(): Boolean {
        return selected.isNotEmpty()
    }
}

/**
 * Open Ended
 *
 * @param id Question ID
 * @param text input text
 */
@Parcelize
data class OpenEndedResponse(
    override val id: Int,
    val text: String?
): Response()
{
    override val type: Int
        get() = QuestionType.OPEN_ENDED.id

    /**
     * @inheritDoc
     */
    override fun isValid(): Boolean {
        return !text.isNullOrEmpty()
    }
}