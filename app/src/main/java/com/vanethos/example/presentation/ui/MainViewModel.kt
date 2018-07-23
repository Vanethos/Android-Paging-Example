package com.vanethos.example.presentation.ui

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel : ViewModel() {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()


    //region RXJava
    fun addDisposable(d : Disposable) = compositeDisposable.add(d)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
    //endregion
}