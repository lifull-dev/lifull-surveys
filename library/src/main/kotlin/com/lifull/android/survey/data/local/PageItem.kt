package com.lifull.android.survey.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Question Page Item
 *
 * @param id ID
 * @param questionItems Question items
 */
@Parcelize
data class PageItem(
    val id: Int,
    val questionItems: List<QuestionItem>
) : Parcelable