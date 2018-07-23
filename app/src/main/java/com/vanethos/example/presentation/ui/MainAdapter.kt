package com.vanethos.example.presentation.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vanethos.example.R
import com.vanethos.example.domain.models.Repos
import com.vanethos.example.utils.pagination.datasource._base.BaseDiffAdapter
import kotlinx.android.synthetic.main.item_repo.view.*

class MainAdapter(listener : ItemClickListener) : BaseDiffAdapter<Repos, MainAdapter.MainViewHolder>() {
    interface ItemClickListener {
        fun onItemClicked(repos: Repos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var repos = getItem(position)
        holder.titleTextView.text = repos?.name
        holder.descriptionTextView.text = repos?.description
        holder.watchersTextView.text = repos?.watchersCount.toString()
        holder.languageTextView.text = repos?.language
        holder.starsTextView.text = repos?.startCount.toString()
    }

    class MainViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.list_item_title_textView
        val descriptionTextView = view.list_item_description_textView
        val watchersTextView = view.list_item_watchers_textView
        val languageTextView = view.list_item_language_textView
        val starsTextView = view.list_item_stars_textView
    }

}