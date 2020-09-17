package com.lifull.android.survey.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Question Page
 *
 * @param id ID
 * @param questions Question items
 */
@Parcelize
data class PageData(
    val id: Int,
    val questions: List<Question<out Choice<Row>>>
) : Parcelable