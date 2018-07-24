package com.vanethos.example.presentation.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vanethos.example.R
import com.vanethos.example.domain.models.Repos
import com.vanethos.example.utils.pagination.datasource._base.BaseDiffAdapter
import kotlinx.android.synthetic.main.item_repo.view.*

class MainAdapter(var listener : ItemClickListener) : BaseDiffAdapter<Repos, RecyclerView.ViewHolder>() {
    interface ItemClickListener {
        fun onItemClicked(repos: Repos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == this.VIEW_TYPE_NORMAL) {
            return MainViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_repo, parent, false)
            )
        } else {
            return MainViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == this.VIEW_TYPE_NORMAL) {
            var repos = getItem(position)
            var viewHolder = holder as MainViewHolder
            viewHolder.titleTextView.text = repos?.name
            viewHolder.descriptionTextView.text = repos?.description
            viewHolder.watchersTextView.text = repos?.watchersCount.toString()
            viewHolder.languageTextView.text = repos?.language
            viewHolder.starsTextView.text = repos?.startCount.toString()
            viewHolder.itemView.setOnClickListener({ v -> listener.onItemClicked(repos!!) })
        }
    }

    class MainViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.list_item_title_textView
        val descriptionTextView = view.list_item_description_textView
        val watchersTextView = view.list_item_watchers_textView
        val languageTextView = view.list_item_language_textView
        val starsTextView = view.list_item_stars_textView
    }

}