package com.lifull.android.survey.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Question
 */
sealed class Question<out : Choice<Row>> : Parcelable {
    /** ID */
    abstract val id: Int
    /** Question Body */
    abstract val message: String
    /** Choice */
    abstract val choice: Choice<Row>
}

/**
 * Single Answer
 */
@Parcelize
class SingleAnswerQuestion(
    override val id: Int,
    override val message: String,
    override val choice: SingleAnswer
): Question<SingleAnswer>()

/**
 * Multiple Answers
 */
@Parcelize
class MultipleAnswersQuestion(
    override val id: Int,
    override val message: String,
    override val choice: MultipleAnswers
): Question<MultipleAnswers>()

/**
 * Open Ended
 */
@Parcelize
class OpenEndedQuestion(
    override val id: Int,
    override val message: String,
    override val choice: OpenEnded
): Question<OpenEnded>()