package com.hojibobo.hojibobopost.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager

/**
 * Created by Bek on 14/12/2016.
 */
object Dialog {
    fun showMessage(
        fragmentManager: FragmentManager?,
        message: String?
    ): MessageDialogFragment {
        val messageDialogFragment = MessageDialogFragment[message]
        messageDialogFragment.show(fragmentManager!!, "ViewAccountsDialog")
        return messageDialogFragment
    }

    fun showMessageConfirm(
        fragmentManager: FragmentManager?,
        message: String?
    ): MessageDialogFragment {
        val messageDialogFragment = MessageDialogFragment.getConfirm(message)
        messageDialogFragment.show(fragmentManager!!, "ViewAccountsDialog")
        return messageDialogFragment
    }

    fun showMessageConfirm(
        fragmentManager: FragmentManager?,
        message: String?,
        message2: String?
    ): MessageDialogFragment {
        val messageDialogFragment =
            MessageDialogFragment.getConfirm(message, message2)
        messageDialogFragment.show(fragmentManager!!, "ViewAccountsDialog")
        return messageDialogFragment
    }

    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoginRequiredDialogMessage(
        supportFragmentManager: FragmentManager?,
        message: String?
    ): MessageDialogFragment {
        val messageDialogFragment = MessageDialogFragment[message]
        messageDialogFragment.isLoginButton = true
        messageDialogFragment.show(supportFragmentManager!!, "LoginViewAccountsDialog")
        return messageDialogFragment
    }
}