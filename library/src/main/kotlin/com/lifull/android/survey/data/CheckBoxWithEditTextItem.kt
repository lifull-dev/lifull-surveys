package com.lifull.android.survey.data

import com.lifull.android.survey.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * CheckBox and EditText Item
 *
 * @param inputText input text
 */
@Parcelize
open class CheckBoxWithEditTextItem(
    override val id: Int = OTHER_RESPONSE_ID,
    override val text: String? = null,
    var inputText: String? = null,
    override var selected: Boolean = false
) : CheckBoxItem(id, text, selected)
{
    override val viewType: Int
        get() = ViewType.CHECKBOX_WITH_EDIT_TEXT.value
}