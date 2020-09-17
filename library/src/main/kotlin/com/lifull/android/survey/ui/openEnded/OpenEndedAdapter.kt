package com.lifull.android.survey.ui.openEnded

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lifull.android.survey.ViewType
import com.lifull.android.survey.data.EditTextItem
import com.lifull.android.survey.ui.common.Adapter
import com.lifull.android.survey.ui.common.EditTextViewHolder
import com.lifull.android.survey.ui.common.QuestionViewHolder
import com.lifull.android.survey.ui.common.ViewHolder
import java.lang.IllegalStateException

/**
 * [Adapter] for OpenEnded
 *
 * @param callback [OpenEndedCallback]
 */
class OpenEndedAdapter(
    private val callback: OpenEndedCallback? = null
) : Adapter(), EditTextViewHolder.Callback
{
    /**
     * @inheritDoc
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (ViewType.fromValue(viewType)) {
            ViewType.HEADER -> {
                LayoutInflater.from(parent.context).inflate(
                    QuestionViewHolder.resourceId,
                    parent,
                    false).let { itemView ->
                    QuestionViewHolder(
                        itemView
                    )
                }
            }
            ViewType.EDIT_TEXT -> {
                LayoutInflater.from(parent.context).inflate(
                    EditTextViewHolder.resourceId,
                    parent,
                    false).let { itemView ->
                    EditTextViewHolder(
                        itemView,
                        this
                    )
                }
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    /**
     * @inheritDoc
     */
    override fun afterTextChanged(position: Int, editable: Editable?) {
        getItem(position, EditTextItem::class.java)?.let { item ->
            item.inputText = editable.toString()
            callback?.afterTextChanged(item.id, editable)
        }
    }
}