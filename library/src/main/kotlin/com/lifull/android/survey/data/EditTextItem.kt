package com.lifull.android.survey.data

import com.lifull.android.survey.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * EditText Item
 *
 * @param hint hint
 * @param inputText input text
 */
@Parcelize
class EditTextItem(
    override val id: Int,
    val hint: String? = null,
    var inputText: String? = null
) : Item()
{
    override val viewType: Int
        get() = ViewType.EDIT_TEXT.value
}