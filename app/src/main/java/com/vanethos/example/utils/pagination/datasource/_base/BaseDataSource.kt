package com.vanethos.example.utils.pagination.datasource._base

import android.arch.paging.PageKeyedDataSource;
import android.util.Log


abstract class BaseDataSource<T> : PageKeyedDataSource<Int, T>() {
    lateinit var onDataSourceLoading: OnDataSourceLoading

    protected abstract fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>)
    protected abstract fun loadAditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, T>)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onFirstFetch()
        }
        loadInitialData(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // since we are keeping data in memory, we will not need to load the data before it.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onDataLoading()
        }
        loadAditionalData(params, callback)
    }


    //region Helpers
    // Helper method to use to submit the initial data
    protected fun submitInitialData(items: List<T>, params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        callback.onResult(items, 0, params.requestedLoadSize)
        if (onDataSourceLoading != null) {
            if (items != null && !items.isEmpty()) {
                onDataSourceLoading!!.onFirstFetchEndWithData()
            } else {
                onDataSourceLoading!!.onFirstFetchEndWithoutData()
            }
        }
    }

    // Helper method to submit next data
    protected fun submitData(items: List<T>, params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // If we reach the limit, then put value NULL as adjacent key to state that the list has ended
        // else, we will configure the next key to be fetched
        callback.onResult(items, if (items == null || items.isEmpty()) null else params.key + items.size)
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onDataLoadingEnd()
        }
    }


    protected fun submitInitialError(error: Throwable) {
        onDataSourceLoading!!.onError()
        error.printStackTrace()
        // You can also have custom error handling with the provided Throwable
    }


    protected fun submitError(error: Throwable) {
        onDataSourceLoading!!.onError()
        error.printStackTrace()
        // You can also have custom error handling with the provided Throwable
    }
    //endregion
}
