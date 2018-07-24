package com.vanethos.example.utils.pagination.factory

import android.arch.paging.DataSource
import com.vanethos.example.domain.models.Repos
import com.vanethos.example.utils.pagination.datasource.ReposDataSource
import com.vanethos.example.utils.pagination.datasource._base.OnDataSourceLoading

/**
 * Factory class that handles the creation of DataSources
 */
class ReposDataSourceFactory(var loading: OnDataSourceLoading,
                             var user: String?) : DataSource.Factory<Int, Repos>() {
    override fun create(): DataSource<Int, Repos>? {
        if (user != null) {
            var source = ReposDataSource(user!!)
            source.onDataSourceLoading = loading
            return source
        }
        return null
    }

}