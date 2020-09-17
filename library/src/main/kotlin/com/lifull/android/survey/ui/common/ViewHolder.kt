package com.lifull.android.survey.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lifull.android.survey.data.Item

/**
 * ViewHolder
 *
 * @param itemView [View]
 */
abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * Bind
     *
     * @param position position
     * @param item item
     */
    abstract fun onBind(position: Int, item: Item)
}