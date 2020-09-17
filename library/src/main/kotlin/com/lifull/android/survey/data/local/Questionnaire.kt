package com.lifull.android.survey.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Questionnaire
 *
 * @param id ID
 * @param name name
 * @param pages page
 * @param configure configure
 * @param createdAt Created Time
 * @param expiredAt Expired Time
 */
@Parcelize
data class Questionnaire(
    val id: Int,
    val name: String,
    val pages: List<PageItem>,
    val configure: QuestionnaireConfigure = QuestionnaireConfigure(),
    val createdAt: Long = System.currentTimeMillis(),
    val expiredAt: Long = System.currentTimeMillis()
) : Parcelable