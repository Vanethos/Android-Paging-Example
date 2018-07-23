package com.vanethos.example.utils.pagination.factory

import android.arch.paging.DataSource
import com.vanethos.example.domain.models.Repos
import com.vanethos.example.utils.pagination.datasource.ReposDataSource
import com.vanethos.example.utils.pagination.datasource._base.OnDataSourceLoading

class ReposDataSourceFactory(var loading: OnDataSourceLoading,
                             var user: String) : DataSource.Factory<Int, Repos>() {
    override fun create(): DataSource<Int, Repos> {
        var source = ReposDataSource(user)
        source.onDataSourceLoading = loading
        return source
    }

}