package com.lifull.android.survey.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Questionnaire Configure
 *
 * @param back text for back
 * @param next text for next
 * @param submit text for submit
 * @param deepLink Deep Link
 */
@Parcelize
data class QuestionnaireConfigure(
    val back: String? = null,
    val next: String? = null,
    val submit: String? = null,
    val deepLink: String? = null
) : Parcelable {
}