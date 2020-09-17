package com.lifull.android.survey.ui.multipleAnswers

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lifull.android.survey.ViewType
import com.lifull.android.survey.data.CheckBoxWithEditTextItem
import com.lifull.android.survey.data.CheckBoxItem
import com.lifull.android.survey.ui.common.*
import java.lang.IllegalStateException

/**
 * [Adapter] for MultipleAnswers
 *
 * @param callback [MultipleAnswersCallback]
 */
class MultipleAnswersAdapter(
    private val callback: MultipleAnswersCallback? = null
) : Adapter(), CheckBoxViewHolder.Callback, CheckBoxWithEditTextViewHolder.Callback
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
            ViewType.CHECKBOX -> {
                LayoutInflater.from(parent.context).inflate(
                    CheckBoxViewHolder.resourceId,
                    parent,
                    false).let { itemView ->
                    CheckBoxViewHolder(
                        itemView,
                        this
                    )
                }
            }
            ViewType.CHECKBOX_WITH_EDIT_TEXT -> {
                LayoutInflater.from(parent.context).inflate(
                    CheckBoxWithEditTextViewHolder.resourceId,
                    parent,
                    false).let { itemView ->
                    CheckBoxWithEditTextViewHolder(
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
    override fun onChecked(position: Int) {
        getItem(position, CheckBoxItem::class.java)?.let {
            it.selected = !it.selected
            callback?.onCheckedChanged(it.id, it.selected, position)

            notifyItemChanged(position)
        }
    }

    /**
     * @inheritDoc
     */
    override fun onChecked(position: Int, editable: Editable?) {
        getItem(position, CheckBoxItem::class.java)?.let {
            it.selected = !it.selected

            if (it is CheckBoxWithEditTextItem) {
                it.inputText = editable?.toString()
            }
            callback?.onCheckedChanged(it.id, it.selected, position)
            notifyItemChanged(position)
        }
    }

    /**
     * @inheritDoc
     */
    override fun afterTextChanged(position: Int, editable: Editable?) {
        getItem(position, CheckBoxWithEditTextItem::class.java)?.let { item ->
            if (item.selected) {
                item.inputText = editable.toString()
                callback?.afterTextChanged(item.id, editable)
            }
        }
    }
}