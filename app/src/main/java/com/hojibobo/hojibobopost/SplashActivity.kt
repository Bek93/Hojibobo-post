package com.hojibobo.hojibobopost

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.hojibobo.hojibobopost.databinding.SplashActivityBinding
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity


class SplashActivity : DaggerAppCompatActivity() {
    private lateinit var activityPlashBinding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        activityPlashBinding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        activityPlashBinding.executePendingBindings()

        if (intent != null && intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY == Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) {
            intent.removeExtra("groupChannelUrl")
        }


        Handler().postDelayed({

            var intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)

    }


}
