package com.lifull.android.survey.ui.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lifull.android.survey.data.*

/**
 * Abstract Adapter
 */
abstract class Adapter : ListAdapter<Item, ViewHolder>(
    DIFF_CALLBACK
) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areContentsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.viewType == oldItem.viewType
            }

            override fun areItemsTheSame(
                oldItem: Item,
                newItem: Item
            ) = oldItem.id == newItem.id
        }
    }

    /**
     * @inheritDoc
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position, getItem(position))
    }

    /**
     * @inheritDoc
     */
    override fun getItemViewType(position: Int) = getItem(position).viewType

    /**
     * Get the item cast to the specidied class
     *
     * @param position Position of item
     * @param clazz Class
     */
    fun <T> getItem(position: Int, clazz: Class<T>): T? {
        return clazz.cast(getItem(position))
    }
}