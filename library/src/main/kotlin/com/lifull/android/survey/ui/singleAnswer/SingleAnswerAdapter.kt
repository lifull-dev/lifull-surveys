package com.lifull.android.survey.ui.singleAnswer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lifull.android.survey.ViewType
import com.lifull.android.survey.data.RadioButtonItem
import com.lifull.android.survey.ui.common.Adapter
import com.lifull.android.survey.ui.common.QuestionViewHolder
import com.lifull.android.survey.ui.common.RadioButtonViewHolder
import com.lifull.android.survey.ui.common.ViewHolder
import java.lang.IllegalStateException

/**
 * [Adapter] for SingleAnswer
 *
 * @param callback [SingleAnswerCallback]
 */
class SingleAnswerAdapter(
    private val callback: SingleAnswerCallback? = null
) : Adapter(), RadioButtonViewHolder.Callback
{
    /** select position */
    var selectedPosition = RecyclerView.NO_POSITION

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
            ViewType.RADIO_BUTTON -> {
                LayoutInflater.from(parent.context).inflate(
                    RadioButtonViewHolder.resourceId,
                    parent,
                    false).let { itemView ->
                    RadioButtonViewHolder(
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
        if (selectedPosition == position) {
            return
        }
        val lastPosition = selectedPosition
        if (lastPosition != RecyclerView.NO_POSITION) {
            getItem(lastPosition, RadioButtonItem::class.java)?.let {
                it.selected = !it.selected
            }
        }

        selectedPosition = position

        getItem(selectedPosition, RadioButtonItem::class.java)?.let {
            it.selected = !it.selected
            callback?.onCheckedChanged(it.id, it.selected, lastPosition, selectedPosition)

            if (lastPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(lastPosition)
            }
            notifyItemChanged(selectedPosition)
        }
    }
}