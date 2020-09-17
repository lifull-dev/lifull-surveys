package com.lifull.android.survey.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lifull.android.survey.R
import com.lifull.android.survey.data.CheckBoxItem
import com.lifull.android.survey.data.Item
import kotlinx.android.synthetic.main.vh_checkbox.view.checkbox
import kotlinx.android.synthetic.main.vh_checkbox_with_edittext.view.*

/**
 * CheckBox
 *
 * @param itemView [View]
 * @param callback [Callback]
 */
class CheckBoxViewHolder(
    itemView: View,
    callback: Callback
) : ViewHolder(itemView)
{
    companion object {
        /** Layout Resource Id */
        val resourceId = R.layout.vh_checkbox
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
            is CheckBoxItem -> {
                itemView.checkbox.text = item.text
                itemView.checkbox.isChecked = item.selected

                if (item.id == CheckBoxItem.OTHER_RESPONSE_ID) {
                    itemView.text.isEnabled = item.selected
                }
            }
        }
    }

    /**
     * Callback Interface
     */
    interface Callback {

        /**
         * Called when check changed
         *
         * @param position position
         */
        fun onChecked(position: Int)
    }
}