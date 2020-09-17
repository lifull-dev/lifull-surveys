package com.lifull.android.survey.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Response Item
 *
 * @param id AnswerID
 * @param label label
 * @param hint hint
 * @param deepLink Deep Link
 */
@Parcelize
data class ResponseItem(
    val id: Int,
    val label: String,
    val hint: String? = null,
    val deepLink: String? = null
) : Parcelable