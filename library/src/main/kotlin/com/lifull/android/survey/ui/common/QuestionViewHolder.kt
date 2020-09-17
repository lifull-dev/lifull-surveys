package com.lifull.android.survey.ui.common

import android.view.View
import com.lifull.android.survey.R
import com.lifull.android.survey.data.Item
import com.lifull.android.survey.data.QuestionItem
import com.lifull.android.survey.ui.common.ViewHolder
import kotlinx.android.synthetic.main.vh_question.view.*

/**
 * Question Body
 *
 * @param itemView [View]
 */
class QuestionViewHolder(
    itemView: View
) : ViewHolder(itemView)
{
    companion object {
        /** Layout Resource Id */
        val resourceId = R.layout.vh_question
    }

    /**
     * @inheritDoc
     */
    override fun onBind(position: Int, item: Item) {
        when (item) {
            is QuestionItem -> {
                itemView.body.text = item.message
            }
        }
    }
}