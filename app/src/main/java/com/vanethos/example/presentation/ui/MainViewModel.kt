package com.vanethos.example.presentation.ui

import android.arch.lifecycle.ViewModel
import com.vanethos.example.domain.models.Repos
import com.vanethos.example.presentation.ui.base.BasePaginationViewModel
import com.vanethos.example.utils.pagination.factory.ReposDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel : BasePaginationViewModel<ReposDataSourceFactory, Repos>() {
    override fun getPageSize(): Int {
        return 3
    }

    init {
        dataSourceFactory = ReposDataSourceFactory(getListener(), null)
    }

    fun newSearch(user : String) {
        dataSourceFactory = ReposDataSourceFactory(getListener(), user)
    }
}