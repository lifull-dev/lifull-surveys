package com.lifull.android.survey.data

import com.lifull.android.survey.ViewType
import kotlinx.android.parcel.Parcelize

/**
 * RadioButton Item
 *
 * @param text label
 * @param selected select status
 */
@Parcelize
class RadioButtonItem(
    override val id: Int,
    val text: String? = null,
    var selected: Boolean = false
) : Item() {
    override val viewType: Int
        get() = ViewType.RADIO_BUTTON.value
}