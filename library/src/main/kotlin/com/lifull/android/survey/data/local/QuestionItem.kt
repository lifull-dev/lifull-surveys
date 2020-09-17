package com.lifull.android.survey.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Question Item
 *
 * @param id ID
 * @param type question type
 * @param body body
 * @param responseItems response items
 */
@Parcelize
data class QuestionItem(
    val id: Int,
    val type: Int,
    val body: String,
    val responseItems: List<ResponseItem>
) : Parcelable