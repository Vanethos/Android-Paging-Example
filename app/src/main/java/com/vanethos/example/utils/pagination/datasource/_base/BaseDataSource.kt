package com.vanethos.example.utils.pagination.datasource._base

import android.arch.paging.PageKeyedDataSource;
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Class that handles how the recyclerview will retrieve data when:
 * @see loadInitial - The recyclerview and adapter are initialized OR when recreating the DataSource
 * @see loadAfter - The user scrolls to retrieve more data
 */
abstract class BaseDataSource<T> : PageKeyedDataSource<Int, T>() {
    lateinit var onDataSourceLoading: OnDataSourceLoading

    private var compositeDisposable = CompositeDisposable()

    protected abstract fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>)
    protected abstract fun loadAditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, T>)

    /**
     * Initial data loaded by the recyclerview,
     * When started, it will load 3 times the number of items "per_page"
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onFirstFetch()
        }
        loadInitialData(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // since we are keeping data in memory, we will not need to load the data before it.
    }

    /**
     * All the data that is fetched when the user scrolls
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        if (onDataSourceLoading != null) {
            onDataSourceLoading!!.onDataLoading()
        }
        loadAditionalData(params, callback)
    }


    //region Helpers
    /**
     * These helper methods will abstract how we handle data and call the necessary methods on the listener
     */
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

    override fun invalidate() {
        super.invalidate()
        compositeDisposable.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
