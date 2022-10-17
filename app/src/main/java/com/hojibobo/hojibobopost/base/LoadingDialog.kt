package com.hojibobo.hojibobopost.base

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.hojibobo.hojibobopost.R

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.loading_dialog)
    }
}