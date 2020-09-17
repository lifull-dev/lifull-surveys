package com.lifull.android.survey

/**
 * Question Types
 *
 * @param id Question ID
 */
enum class QuestionType constructor(val id: Int) {
    /** single answer question */
    SINGLE_ANSWER(1),
    /** multiple answer question */
    MULTIPLE_ANSWERS(2),
    /** open ended question */
    OPEN_ENDED(3);

    companion object {
        /**
         * Get the [ViewType] of the specified ID
         *
         * @param id ID
         * @return [ViewType]
         */
        fun fromId(id: Int) = values().first { it.id == id }
    }
}