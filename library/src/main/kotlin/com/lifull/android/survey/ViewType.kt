package com.lifull.android.survey

/**
 * ViewType
 *
 * @param value value of ViewType
 */
enum class ViewType constructor(val value: Int) {
    HEADER(1),
    EDIT_TEXT(2),
    CHECKBOX(3),
    RADIO_BUTTON(4),
    CHECKBOX_WITH_EDIT_TEXT(5);

    companion object {
        /**
         * Get the [ViewType] of the specified value
         *
         * @param value 種類
         * @return value を持つ [ViewType]
         */
        fun fromValue(value: Int) = values().first { it.value == value }
    }
}