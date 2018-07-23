package com.vanethos.example.presentation.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.vanethos.example.presentation.utils.Event
import com.vanethos.example.utils.pagination.datasource._base.OnDataSourceLoading
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePaginationViewModel<T : DataSource.Factory<Int, K>, K> : ViewModel() {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    protected lateinit var dataSourceFactory : T
    private var pagedObservable: Observable<PagedList<K>>? = null
    /**
     * [clearDataEvents] acts as an "event", rather than state. [Observer]s
     * are notified of the change as usual with [LiveData], but only one [Observer]
     * will actually read the data. For more information, check the [Event] class.
     */
    val clearDataEvents : MutableLiveData<Event<Unit>> get() = _clearDataEvents
    private val _clearDataEvents = MutableLiveData<Event<Unit>>()

    val emptyVisibilityEvents : MutableLiveData<Event<Boolean>> get() = _emptyVisibilityEvents
    private val _emptyVisibilityEvents = MutableLiveData<Event<Boolean>>()

    val recyclerViewLoadingEvents : MutableLiveData<Event<Boolean>> get() = _recyclerViewLoadingEvents
    private val _recyclerViewLoadingEvents = MutableLiveData<Event<Boolean>>()

    val errorToastEvent : MutableLiveData<Event<Unit>> get() = _errorToastEvent
    private val _errorToastEvent = MutableLiveData<Event<Unit>>()

    abstract fun getPageSize() : Int

    //region Pagination
    fun clearData() {
        this.clearDataEvents.postValue(Event(Unit))
    }

    fun getItems(): Observable<PagedList<K>>? {
        if (pagedObservable == null) {
            createPagedObservable()
        }
        return pagedObservable
    }

    fun createPagedObservable() {
        pagedObservable = RxPagedListBuilder(
                dataSourceFactory,
                PagedList.Config.Builder()
                        .setPageSize(getPageSize())
                        .setEnablePlaceholders(false)
                        .build())
                .buildObservable()
    }

    protected fun getListener(): OnDataSourceLoading {
        return object : OnDataSourceLoading {
            override fun onFirstFetch() {
                showRecyclerLoading()
            }

            override fun onFirstFetchEndWithData() {
                hideRecyclerLoading()
                hideEmptyVisibility()
            }

            override fun onFirstFetchEndWithoutData() {
                hideRecyclerLoading()
                showEmptyVisibility()

            }

            override fun onDataLoading() {
                showRecyclerLoading()
            }

            override fun onDataLoadingEnd() {
                hideRecyclerLoading()
            }

            override fun onInitialError() {
                hideRecyclerLoading()
                showEmptyVisibility()
                showErrorToast()
            }

            override fun onError() {
                hideRecyclerLoading()
                showEmptyVisibility()
                showErrorToast()
            }
        }
    }
    //endregion

    //region empty visibility
    fun showEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(true))
    }

    fun hideEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(false))
    }
    //endregion

    //region RecyclerView Loading
    fun showRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(true))
    }

    fun hideRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(false))
    }
    //endregion

    //region show error toast
    fun showErrorToast() {
        errorToastEvent.postValue(Event(Unit))
    }
    //endregion

    //region RXJava
    fun addDisposable(d : Disposable) = compositeDisposable.add(d)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
    //endregion
}