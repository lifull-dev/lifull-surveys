package com.lifull.android.survey.data

import com.lifull.android.survey.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * CheckBox Item
 *
 * @param text label
 * @param selected selected status
 */
@Parcelize
open class CheckBoxItem(
    override val id: Int,
    open val text: String? = null,
    open var selected: Boolean = false
) : Item()
{
    companion object {
        /** ID for [OpenEnded] */
        const val OTHER_RESPONSE_ID = -1
    }

    override val viewType: Int
        get() = when (id) {
            OTHER_RESPONSE_ID -> {
                ViewType.CHECKBOX_WITH_EDIT_TEXT.value
            }
            else -> {
                ViewType.CHECKBOX.value
            }
        }
}