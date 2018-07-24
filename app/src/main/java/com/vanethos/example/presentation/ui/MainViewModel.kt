package com.vanethos.example.presentation.ui

import android.arch.lifecycle.ViewModel
import com.vanethos.example.domain.models.Repos
import com.vanethos.example.presentation.ui.base.BasePaginationViewModel
import com.vanethos.example.utils.pagination.factory.ReposDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel : BasePaginationViewModel<ReposDataSourceFactory, Repos>() {
    init {
        dataSourceFactory = ReposDataSourceFactory(getListener(), null)
    }

    override fun getPageSize(): Int = 3

    /**
     * Handles a new user search
     */
    fun newSearch(user : String) {
        dataSourceFactory.user = user
        clearData()
    }
}