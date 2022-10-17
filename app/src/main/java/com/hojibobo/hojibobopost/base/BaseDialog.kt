/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.hojibobo.hojibobopost.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.io.Serializable
import java.util.*

/**
 * Created by Bek on 12/06/19.
 */

abstract class BaseDialog<VB : ViewDataBinding>(@LayoutRes val layoutRes: Int) : DialogFragment(),
    BaseNavigator {

    var baseActivity: BaseActivity<*, *>? = null
        private set

    lateinit var binding: VB
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            if (isDialogShowFull()) {
                dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<VB>(
            inflater, layoutRes, container,
            false
        )
        return binding.root
    }


    open fun isDialogShowFull(): Boolean = false


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // creating the fullscreen dialog
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }



    protected fun hasBackIcon(): Boolean {
        return false
    }

    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }


    override fun hideLoading() {
        if (baseActivity != null) {
            baseActivity!!.hideLoading()
        }
    }

    override fun showLoading() {
        if (baseActivity != null) {
            baseActivity!!.showLoading()
        }
    }


    fun openNextActivityWithData(nextActivity: Class<*>, serializabledObject: Serializable) {
        val intent = Intent(context, nextActivity)
        intent.putExtra("data", serializabledObject)
        startActivity(intent)
    }

    override fun openNextActivity(intent: Intent) {

    }

    override fun openNextActivityClearTop(intent: Intent) {

    }


    override fun finishActivityOnViewModel() {

    }


    override fun handleError(throwable: Throwable) {

    }

    override fun showDialogMessage(message: String) {
        TODO("Not yet implemented")
    }


}
