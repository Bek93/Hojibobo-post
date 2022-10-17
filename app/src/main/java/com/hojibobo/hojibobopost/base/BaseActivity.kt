package com.hojibobo.hojibobopost.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hojibobo.hojibobopost.R
import com.hojibobo.hojibobopost.BR
import com.hojibobo.hojibobopost.databinding.ActionBarBinding
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel>(
    private val viewModelClass: Class<VM>,
    @LayoutRes val layoutRes: Int
) :
    DaggerAppCompatActivity(), BaseNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: VB
    lateinit var viewModel: VM
    lateinit var loadingDialog: LoadingDialog

    open fun hasActionBar(): Boolean = true
    open fun hasTitle(): Boolean = true
    open fun hasBackIcon(): Boolean = true
    open fun getActionBarLayout(): Int = R.layout.action_bar
    open fun getActionBarTitleId(): Int = R.string.app_name

    private var mActionbar: ActionBar? = null
    private var mActionToolbar: Toolbar? = null
    private var actionbar_title: TextView? = null
    lateinit var actionBarBinding: ActionBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create data binding view
        loadingDialog = LoadingDialog(this)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        //provide view model
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        //assign view model to viewModel data binding layout variable
        binding.setVariable(BR.viewModel, viewModel)
        //execute binding changes
        binding.executePendingBindings()
        setActionbar()
    }

    private fun setActionbar() {

        if (hasActionBar()) {
            mActionToolbar = binding.root.findViewById(R.id.actionBar)
            if (mActionToolbar != null) {
                setSupportActionBar(mActionToolbar)
                if (hasTitle()) {
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    supportActionBar!!.setHomeButtonEnabled(false)
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    actionbar_title =
                        mActionToolbar!!.findViewById<View>(R.id.actionbarTitle) as TextView
                    setActionBarTitle(getActionBarTitleId())

                }

                if (hasBackIcon()) {
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                    supportActionBar!!.setHomeButtonEnabled(true)
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_icon)
                    mActionToolbar!!.setNavigationOnClickListener { onBackPressed() }
                }
            }
        }
    }


    fun setActionBarTitle(actionBarTitleId: Int) {
        if (actionbar_title != null)
            actionbar_title!!.text = getString(actionBarTitleId)
    }

    fun setActionBarTitle(actionBarTitle: String) {
        if (actionbar_title != null)
            actionbar_title!!.text = actionBarTitle
    }



    override fun openNextActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun openNextActivityClearTop(intent: Intent) {
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }


    override fun finishActivityOnViewModel() {
        finish()
    }


    override fun showDialogMessage(message: String) {
        Dialog.showMessage(supportFragmentManager, message)
    }


    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        if (loadingDialog.isShowing)
            loadingDialog.dismiss()
    }

    override fun handleError(throwable: Throwable) {

    }
}