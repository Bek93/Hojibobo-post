package com.hojibobo.hojibobopost.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hojibobo.hojibobopost.R
import com.hojibobo.hojibobopost.databinding.MessageDialogFragmentBinding
import java.util.*

/**
 * Created by bek on 01/08/2017.
 */
class MessageDialogFragment :
    BaseDialog<MessageDialogFragmentBinding>(R.layout.message_dialog_fragment) {
    private var messageDialogClickListener: MessageDialogClickListener? = null
    private var message1: String? = null
    var isLoginButton = false

    fun setMessageDialogClickListener(messageDialogClickListener: MessageDialogClickListener?) {
        this.messageDialogClickListener = messageDialogClickListener
    }

    private var message: String? = null
    private var confirm = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirm = arguments!!.getInt("confirm", 0)
        message = arguments!!.getString("message")
        message1 = arguments!!.getString("message1")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MessageDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun isDialogShowFull(): Boolean {
        return false
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.messageText.setText(message)
        if (message1 != null) {
            binding.secondaryMessageText.setText(message)
        } else {
            binding.secondaryMessageText.visibility = View.GONE
        }
        if (isLoginButton) {
            binding.confirm.text = "Login"
        }
        if (confirm == 0) {
            binding.cancel.visibility = View.GONE
        }
        binding.confirm.setOnClickListener(View.OnClickListener {
            dismiss()
            if (messageDialogClickListener != null) {
                messageDialogClickListener!!.confirmClick()
            }
        })
        binding.cancel.setOnClickListener(View.OnClickListener {
            dismiss()
            if (messageDialogClickListener != null) {
                messageDialogClickListener!!.cancelClick()
            }
        })
    }

    override fun openNextActivity(intent: Intent) {
        startActivity(intent)
    }

    fun finishActivityFromViewModel() {
        activity!!.finish()
    }

    companion object {
        operator fun get(confirm: Int, message: String?): MessageDialogFragment {
            val messageDialogFragment = MessageDialogFragment()
            val bundle = Bundle()
            bundle.putInt("confirm", confirm)
            bundle.putString("message", message)
            messageDialogFragment.arguments = bundle
            return messageDialogFragment
        }

        operator fun get(message: String?): MessageDialogFragment {
            val messageDialogFragment = MessageDialogFragment()
            val bundle = Bundle()
            bundle.putString("message", message)
            messageDialogFragment.arguments = bundle
            return messageDialogFragment
        }

        fun getConfirm(message: String?): MessageDialogFragment {
            val messageDialogFragment = MessageDialogFragment()
            val bundle = Bundle()
            bundle.putString("message", message)
            bundle.putInt("confirm", 1)
            messageDialogFragment.arguments = bundle
            return messageDialogFragment
        }

        fun getConfirm(message: String?, message1: String?): MessageDialogFragment {
            val messageDialogFragment = MessageDialogFragment()
            val bundle = Bundle()
            bundle.putString("message", message)
            bundle.putString("message1", message1)
            bundle.putInt("confirm", 1)
            messageDialogFragment.arguments = bundle
            return messageDialogFragment
        }
    }
}