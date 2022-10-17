package com.hojibobo.hojibobopost

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hojibobo.hojibobopost.base.BaseViewModel
import com.hojibobo.hojibobopost.base.PreferencesManager
import com.hojibobo.hojibobopost.model.Order
import com.hojibobo.hojibobopost.model.User
import com.sendbird.sbsample.rx.SchedulerProvider
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainViewModel @Inject constructor(
    context: Context,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<MainNavigator>(
        schedulerProvider
    ) {

    var preferencesManager = PreferencesManager(context)
    var orderArrayListLiveData = MutableLiveData<ArrayList<Order>>()
    private val mainApi: MainService = ApiService.provideApi(MainService::class.java, context)

    fun createUser() {
        var user = User()

        user.username = getUUID()
        user.password = getUUID()
        getNavigator().showLoading()
        compositeDisposable.add(
            mainApi.signUp(user)
                .subscribeOn(schedulerProvider!!.io())
                .observeOn(schedulerProvider!!.ui())
                .subscribe({
                    preferencesManager.user = it
                    preferencesManager.setSignedIn(1)
                    getTodayAcceptedOrderList()

                }, { t ->
                    getNavigator().hideLoading()
                    getNavigator().handleError(t)
                    login()
                })
        );


    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayAcceptedOrderList() {
        //var today = SimpleDateFormat("YYYY-MM-dd").format(Date())
        var today = "2020-08-19"
        compositeDisposable.add(
            mainApi.getTodayAcceptOrderList(today)
                .subscribeOn(schedulerProvider!!.io())
                .observeOn(schedulerProvider!!.ui())
                .subscribe({
                    orderArrayListLiveData.value = it!!
                }, { t ->
                    getNavigator().hideLoading()
                    getNavigator().handleError(t)
                })
        );
    }


    fun getUUID(): String {
        val uuid = "1000" + //we make this look like a valid IMEI
                Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10
        return uuid
    }

    @SuppressLint("CheckResult")
    fun login() {
        var user = User()
        getNavigator().showLoading()
        user.username = getUUID()
        user.password = getUUID()
        mainApi.signIn(user)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                preferencesManager.user = it!!
                preferencesManager.setComplete(it.isComplete())
                preferencesManager.setSignedIn(1)
                getNavigator().hideLoading()
                getTodayAcceptedOrderList()
            }, { t ->
                getNavigator().hideLoading()
                getNavigator().handleError(t)
            })
    }

    fun makeOrderAsSent(postCode: String?, selectedOrder: Order) {
        compositeDisposable.add(
            mainApi.sendAndNotify(selectedOrder.id!!, postCode!!)
                .subscribeOn(schedulerProvider!!.io())
                .observeOn(schedulerProvider!!.ui())
                .subscribe({
                    getTodayAcceptedOrderList()
                }, { t ->
                    getNavigator().hideLoading()
                    getNavigator().handleError(t)
                })
        );
    }

}