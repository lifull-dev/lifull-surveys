package com.lifull.android.survey.data

import com.lifull.android.survey.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * Question Item
 *
 * @param message question body
 */
@Parcelize
class QuestionItem(
    override val id: Int,
    val message: String?
) : Item()
{
    override val viewType: Int
        get() = ViewType.HEADER.value
}