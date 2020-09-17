package com.lifull.android.survey.data

import android.os.Parcelable

/**
 * Item for [RecyclerView.Adapter]
 */
abstract class Item : Parcelable {
    /** Item ID */
    abstract val id: Int
    /** ViewType */
    abstract val viewType: Int
}