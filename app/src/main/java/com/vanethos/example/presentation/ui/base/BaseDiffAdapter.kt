package com.vanethos.example.presentation.ui.base

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View

// Since we will have two views, we need to specify a view type for each
const val VIEW_TYPE_NORMAL = 0
const val VIEW_TYPE_LOADING = 1

abstract class BaseDiffAdapter<T, ViewHolder : RecyclerView.ViewHolder>(diffCallback : DiffUtil.ItemCallback<T> = defaultCallback.defaultDiffCallback()) :
        PagedListAdapter<T, ViewHolder>(diffCallback) {

    // when the adapter is not loading, we want to update it in order to remove the last entry
    var loading: Boolean = true
        set(value) {
            field = value
            if (!loading) {
                notifyDataSetChanged()
            }
        }

    protected inner class LoadingViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return if (loading && position == itemCount - 1) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    // Required defaultcallback for DiffUtils
    object defaultCallback {
        fun <T> defaultDiffCallback(): DiffUtil.ItemCallback<T> {
            return object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}

