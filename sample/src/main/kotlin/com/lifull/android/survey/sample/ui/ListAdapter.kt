package com.lifull.android.survey.sample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.lifull.android.survey.sample.R
import kotlinx.android.synthetic.main.vh_questionnaire.view.*
import java.util.*

class ListAdapter(
    var items: MutableList<ListItem>,
    private val callback: ((id: Int) -> Unit)? = null
): androidx.recyclerview.widget.ListAdapter<ListItem, ViewHolder>(
    DIFF_CALLBACK
) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListItem, newItem: ListItem
            ) = oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_questionnaire, parent, false)
        return ViewHolder(view).apply {
            itemView.setOnClickListener {
                val item = items.getOrNull(bindingAdapterPosition) ?: return@setOnClickListener
                callback?.invoke(item.id)
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items.getOrNull(position)?.let { item ->
            holder.itemView.textView_name.text = item.name
            holder.itemView.textView_createdAt.text = Date(item.createdAt).toString()
            holder.itemView.textView_expiredAt.text = Date(item.expiredAt).toString()
        }
    }
}