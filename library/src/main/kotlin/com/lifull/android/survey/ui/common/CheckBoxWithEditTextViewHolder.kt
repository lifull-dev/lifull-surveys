package com.lifull.android.survey.ui.common

import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.lifull.android.survey.R
import com.lifull.android.survey.data.CheckBoxWithEditTextItem
import com.lifull.android.survey.data.Item
import kotlinx.android.synthetic.main.vh_checkbox_with_edittext.view.*

/**
 * CheckBox, EditText
 *
 * @param itemView [View]
 * @param callback [Callback]
 */
class CheckBoxWithEditTextViewHolder(
    itemView: View,
    callback: Callback
) : ViewHolder(itemView)
{
    companion object {
        /** Layout Resource Id */
        val resourceId = R.layout.vh_checkbox_with_edittext
    }

    init {
        itemView.setOnClickListener {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                callback.onChecked(position, itemView.text.text)
            }
        }
        itemView.text.addTextChangedListener { editable ->
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                callback.afterTextChanged(position, editable)
            }
        }
    }

    /**
     * @inheritDoc
     */
    override fun onBind(position: Int, item: Item) {
        when (item) {
            is CheckBoxWithEditTextItem -> {
                itemView.checkbox.text = item.text
                itemView.checkbox.isChecked = item.selected
                itemView.text.isEnabled = item.selected
                itemView.text.setText(item.inputText)
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
         * @param editable input string
         */
        fun onChecked(position: Int, editable: Editable?)

        /**
         * Called when text changed
         *
         * @param position position
         * @param editable input string
         */
        fun afterTextChanged(position: Int, editable: Editable?)
    }
}