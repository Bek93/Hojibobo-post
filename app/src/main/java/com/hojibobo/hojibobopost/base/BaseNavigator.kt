package com.hojibobo.hojibobopost.base

import android.content.Intent

interface BaseNavigator {

    fun openNextActivity(intent: Intent)

    fun openNextActivityClearTop(intent: Intent)

    fun finishActivityOnViewModel()

    /**
     * @param message is a text to show popup dialog
     * @see BaseNavigator
     */
    fun showDialogMessage(message: String)

    fun showLoading()

    fun hideLoading()

    fun handleError(throwable: Throwable)
}