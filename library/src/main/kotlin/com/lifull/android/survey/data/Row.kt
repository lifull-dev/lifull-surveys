package com.lifull.android.survey.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Display content
 */
sealed class Row : Parcelable {
    // ID
    abstract val id: Int
}

/**
 * Single Answer
 *
 * @param label label
 */
@Parcelize
data class RadioButtonRow(
    override val id: Int,
    val label: String?
) : Row()

/**
 * Multiple Answer
 *
 * @param label label
 * @param hint hint
 */
@Parcelize
data class CheckBoxRow(
    override val id: Int,
    val label: String?,
    val hint: String?
) : Row()

/**
 * Open Ended
 *
 * @param hint hint
 */
@Parcelize
data class EditTextRow(
    override val id: Int,
    val hint: String?
) : Row()

/**
 * Body
 *
 * @param message body
 */
@Parcelize
data class MessageRow(
    override val id: Int,
    val message: String?
) : Row()