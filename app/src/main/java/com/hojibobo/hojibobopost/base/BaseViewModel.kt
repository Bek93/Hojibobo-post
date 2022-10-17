package com.hojibobo.hojibobopost.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.sendbird.sbsample.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BaseViewModel<T>(var schedulerProvider: SchedulerProvider) : ViewModel() {

    var loading = ObservableBoolean(false)
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    lateinit var mNavigator: WeakReference<T>

    fun setNavigator(navigator: T) {
        mNavigator = WeakReference<T>(navigator)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getNavigator(): T {
        return mNavigator.get()!!
    }

    fun isLoading(): ObservableBoolean {
        return loading
    }

    fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }


}