package com.vanethos.example.utils.pagination.datasource._base

/**
 * Interface that will let us communicate between
 * @see BaseDataSource class
 * and
 * @see com.vanethos.example.presentation.ui.base.BasePaginationViewModel
 */
interface OnDataSourceLoading {
    fun onFirstFetch()
    fun onFirstFetchEndWithData()
    fun onFirstFetchEndWithoutData()
    fun onDataLoading()
    fun onDataLoadingEnd()
    fun onInitialError()
    fun onError()
}