package com.lifull.android.survey.ui.common

import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.lifull.android.survey.R
import com.lifull.android.survey.data.EditTextItem
import com.lifull.android.survey.data.Item
import kotlinx.android.synthetic.main.vh_edittext.view.*

/**
 * EditText
 *
 * @param itemView [View]
 * @param callback [Callback]
 */
class EditTextViewHolder(
    itemView: View,
    callback: Callback
) : ViewHolder(itemView)
{
    companion object {
        /** Layout Resource Id */
        val resourceId = R.layout.vh_edittext
    }

    init {
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
            is EditTextItem -> {
                itemView.text.setText(item.inputText)
                itemView.text.hint = item.hint
            }
        }
    }

    /**
     * Callback Interface
     */
    interface Callback {

        /**
         * Called when text changed
         *
         * @param position position
         * @param editable input text
         */
        fun afterTextChanged(position: Int, editable: Editable?)
    }
}