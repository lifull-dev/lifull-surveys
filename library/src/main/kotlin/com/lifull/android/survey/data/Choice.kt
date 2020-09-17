package com.lifull.android.survey.data

import android.os.Parcelable
import com.lifull.android.survey.*
import kotlinx.android.parcel.Parcelize

/**
 * Choice Item for Question
 */
sealed class Choice<out Row> : Parcelable {
    /** Type of answer */
    abstract val type: QuestionType
    /** Display contents */
    abstract val rows: List<Row>
}

/**
 * Single Answer
 */
@Parcelize
data class SingleAnswer(
    override val rows: List<RadioButtonRow>
): Choice<RadioButtonRow>()
{
    override val type: QuestionType
        get() = QuestionType.SINGLE_ANSWER
}

/**
 * Multiple Answers
 */
@Parcelize
data class MultipleAnswers(
    override val rows: List<CheckBoxRow>
): Choice<CheckBoxRow>()
{
    override val type: QuestionType
        get() = QuestionType.MULTIPLE_ANSWERS
}

/**
 * Open Ended
 */
@Parcelize
data class OpenEnded(
    override val rows: List<EditTextRow>
): Choice<EditTextRow>()
{
    override val type: QuestionType
        get() = QuestionType.OPEN_ENDED
}