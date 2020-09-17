package com.lifull.android.survey.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lifull.android.survey.R
import com.lifull.android.survey.data.Item
import com.lifull.android.survey.data.RadioButtonItem
import kotlinx.android.synthetic.main.vh_radio.view.*

/**
 * RadioButton
 *
 * @param itemView [View]
 * @param callback [Callback]
 */
class RadioButtonViewHolder(
    itemView: View,
    callback: Callback
) : ViewHolder(itemView)
{
    companion object {
        /** Layout Resource Id */
        val resourceId = R.layout.vh_radio
    }

    init {
        itemView.setOnClickListener {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                callback.onChecked(position)
            }
        }
    }

    /**
     * @inheritDoc
     */
    override fun onBind(position: Int, item: Item) {
        when (item) {
            is RadioButtonItem -> {
                itemView.button.text = item.text
                itemView.button.isChecked = item.selected
            }
        }
    }

    /**
     * Callback Interface
     */
    interface Callback {

        /**
         * Called when button checked
         *
         * @param position position
         */
        fun onChecked(position: Int)
    }
}